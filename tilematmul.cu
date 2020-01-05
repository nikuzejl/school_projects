/** tilematmul.c
 *
 * Lab 11 Part 2, CPSC 375.
 * This program does basic matrix multiplication with CUDA.
 * This program uses the more sophisticated block matrix mult algorithm.
 *
 * Run as:
 * 	./tilematmul n
 *
 * where n is the size of the nxn matrix and is divisible by the tilesize, 16
 *
 * 		
 	    n	  Time
 	----------------------
        16	 0.024576 ms
       256	 1.771360 ms
       512	 14.059648 ms
      1024	 108.453156 ms
      2048	 686.185242 ms
      4096	 5158.414062 ms
      
 *
 * Author: Bettina, Lewis
 * Date: 12/9/2019
 *
 */
 
#include <stdio.h>
#include <stdlib.h>

const int TILE_WIDTH = 16;

__global__ void TiledMatrixMulKernel(double* M, double* N, double* P, int Width) {
	
	__shared__ double ds_M[TILE_WIDTH][TILE_WIDTH];
	__shared__ double ds_N[TILE_WIDTH][TILE_WIDTH];
	
	int bx = blockIdx.x; int by = blockIdx.y;
	int tx = threadIdx.x; int ty = threadIdx.y;
	
	int Row = by * TILE_WIDTH + ty;
	int Col = bx * TILE_WIDTH + tx;
	double Pvalue = 0;
	
	// Loop over the M and N tiles required to compute the P element
	for (int ph = 0; ph < Width/TILE_WIDTH; ++ph) {
	
		// Collaborative loading of M and N tiles into shared memory
		ds_M[ty][tx] = M[Row*Width + ph*TILE_WIDTH + tx];
		ds_N[ty][tx] = N[(ph*TILE_WIDTH + ty)*Width + Col];
		__syncthreads();
	
		for (int i = 0; i < TILE_WIDTH; ++i)
			Pvalue += ds_M[ty][i] * ds_N[i][tx];
		
		__syncthreads();
	}
	
	P[Row*Width+Col] = Pvalue;
}

int main(int argc, char* argv[]) {

	int 				n = atoi(argv[1]);
	int 				size;
	double 				*a, *b, *c, *d_a, *d_b, *d_c;
	double				*ap, *bp;
	int				x;
	float 				elapsedTime;
        cudaEvent_t 		        start, stop;
	
	// allocate space
	size = n*n*sizeof(double);
	a = (double *) malloc(size);
	b = (double *) malloc(size);
	c = (double *) malloc(size);
	
	// fill dummy matrices
	ap = a; 
	bp = b;
	for (x = 0; x < n*n; x++) {
		*ap++ = 1;
		*bp++ = 1;
	}
	
	
	// allocate device space
	cudaMalloc((void**) &d_a, size);
	cudaMalloc((void**) &d_b, size);
	cudaMalloc((void**) &d_c, size);
	
	// copy input arrays to device
	cudaMemcpy(d_a, a, size, cudaMemcpyHostToDevice);
	cudaMemcpy(d_b, b, size, cudaMemcpyHostToDevice);
	
	// start timer
	cudaEventCreate(&start);
        cudaEventCreate(&stop);
        cudaEventRecord(start, 0);
    
        // do matrix multiplication on device
        dim3 DimGrid((n-1)/16+1, (n-1)/16+1, 1);
	dim3 DimBlock(16,16,1);
	TiledMatrixMulKernel<<<DimGrid, DimBlock>>>(d_a, d_b, d_c, n); 
	
	// stop timer
        cudaEventRecord(stop, 0);
        cudaEventSynchronize(stop);
        cudaEventElapsedTime(&elapsedTime, start, stop);
	
	// copy out results
	cudaMemcpy(c, d_c, size, cudaMemcpyDeviceToHost);
    
        // print results
        printf("%10d\t %f ms\n", n, elapsedTime);
	

	// free vars
	cudaEventDestroy(start);
        cudaEventDestroy(stop);
	free(a); free(b); free(c);
	
	
	return 0;
}
