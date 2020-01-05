/*
J. Lewis Nikuze
CSPC 375
Assignment 9

Blurring a picture using QDMBP and and CUDA
*/


 #include "qdbmp.h"
 #include <stdio.h>

 #define BLUR_SIZE   7
 #define CHANNELS    3	

__global__ void blurKernel(unsigned char * in, unsigned char * out, int w, int h) {
    int Col  = blockIdx.x * blockDim.x + threadIdx.x;
    int Row  = blockIdx.y * blockDim.y + threadIdx.y;
    if (Col < w && Row < h) {
        int pixVal = 0;
        int pixels = 0;
        // Get the average of the surrounding 2xBLUR_SIZE x 2xBLUR_SIZE box
        for (int blurRow = -BLUR_SIZE; blurRow< BLUR_SIZE+1; ++blurRow) {
            for (int blurCol = -BLUR_SIZE; blurCol< BLUR_SIZE+1; ++blurCol) {
                int curRow = Row + blurRow;
                int curCol = Col + blurCol; // Verify we have a valid image pixel
                if (curRow> -1 && curRow< h && curCol> -1 && curCol< w) {
                    pixVal += in[curRow* w + curCol];
                    pixels++; // Keep track of number of pixels in the accumulated total
                }
            }
        } // Write our new pixel value out
        out[Row * w + Col] = (unsigned char) (pixVal/ pixels);
    } 
}

int main( int argc, char* argv[] )
{       UCHAR r,g,b;
	UINT	width, height;
	BMP*	bmp;
        unsigned char *dev_in;
        unsigned char *dev_out;
	int x, y, size;
	unsigned char *array;

	/* Read an image file */
	bmp = BMP_ReadFile( argv[ 1 ] );
	BMP_CHECK_ERROR( stdout, -1 );

	/* Get image's dimensions */
	width = BMP_GetWidth( bmp );
	height = BMP_GetHeight( bmp );

        size = sizeof(unsigned char) *width*height*CHANNELS;

        cudaMalloc((void**) &dev_in, size);           // allocates out on device 
        cudaMalloc((void**) &dev_out,size);           // allocates in on device

	array = (unsigned char *) malloc(size);

        //mapping a bmp file to an array of unsigned chars
	for(x=0; x < width; x++){
   	  for(y= 0 ; y< height; y++){
       	    UCHAR r,g,b;
      	    BMP_GetPixelRGB(bmp,x,y,&r, &g, &b);

            array[x*CHANNELS*width + CHANNELS*y] = r;
            array[x*CHANNELS*width + CHANNELS*y+1] = g;
            array[x*CHANNELS*width + CHANNELS*y+2] = b;
         }
        }

        cudaMemcpy(dev_in, array, size, cudaMemcpyHostToDevice);

        dim3 DimGrid(((CHANNELS*width)-1)/16 + 1, (height-1)/16 + 1, 1);
        dim3 DimBlock(16, 16, 1);

        blurKernel<<<DimGrid, DimBlock>>>(dev_in, dev_out, width*3, height);

        cudaMemcpy(array, dev_out, size, cudaMemcpyDeviceToHost);

        //mapping the array to a bmp file
	for(x=0; x < width; x++){
  	  for(y= 0; y< height;y++){
     	    r = array[x*CHANNELS*width + CHANNELS*y ];
            g = array[x*CHANNELS*width + CHANNELS*y+1];
       	    b = array[x*CHANNELS*width + CHANNELS*y+2];

            BMP_SetPixelRGB(bmp,x,y,r,g,b);
          }
        }

	/* Save result */
	BMP_WriteFile( bmp, argv[ 2 ] );
	BMP_CHECK_ERROR( stdout, -2 );

	/* Free all memory allocated for the image */
	BMP_Free( bmp );

        cudaFree(dev_in);
        cudaFree(dev_out);

	return 0;
}
