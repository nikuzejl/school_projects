# Makefile for CPSC 375
# Assignment 9
#
NVCC = nvcc
OPTION = -arch=sm_35
SRC = imgblur.cu
OBJ = imgblur.o 
BMPSRC = qdbmp.cpp
BMPHDR = qdbmp.h
BMPOBJ = qdbmp.o
GRD = $(SRC) $(BMPSRC) $(BMPHDR) Makefile 

imgblur: $(OBJ) $(BMPOBJ)
	$(NVCC) $(OPTION) -o imgblur $(OBJ) $(BMPOBJ) 

imgblur.o: imgblur.cu
	$(NVCC) $(OPTION) -c imgblur.cu

qdbmp.o: qdbmp.cpp
	$(NVCC) -c qdbmp.cpp

clean:
	rm -f imgblur $(OBJ) $(BMPOBJ)

grade:
	tar cvf - $(GRD) > myassn09.tar
