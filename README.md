
The idea in brief is a splitter function which splits the rows after reading the file with the first row as the matrix size.
char** splitter(char* tmp_str, const char a_delimiter)

The function named counter() prints the output to the file named “output.txt” the Matrices components and total elapsed time.
void counter(int * array)

The Main function reads the file named “source.txt” and saves them in Matrices named “Matrix1,Matrix2”




MatrixMulti() contains the full logic of multiplication in the first variation (row) which calculates the matrix by using the row index  and the second variation (element) which uses the coordinates of the element and launches the calculation process.
void *MatrixMul(void *vargp)
Threads in the row variation are equal to number of rows, while in element variation depending on the count of index.
Threads are created using pthreads library.

Merge sort is made using the same technique while the difference is the calculation process which is recursive and splits the array into half with each call and creates thread until no more splits can be made.


  
 
