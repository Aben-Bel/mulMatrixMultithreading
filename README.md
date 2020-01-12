# mulMatrixMultithreading

Use socket that hosts a server, and accepts connection from client and worker. 
Get matrix from client and Call worker to do the multiplication one by one.


# To run the program:

## 1 First launch server.java
## 2 Then run client.java
  In the client console give two matrix
  
    example 1 for 1 2 3 and 1 0 0
                  4 5 6     0 1 0
                  7 8 9     0 0 1
  
  Input : 1 2 3,4 5 6,7 8 9|1 0 0,0 1 0,0 0 1
  
  
    example 2 for 1 0 0 and   1 2 3
                  0 1 0       4 5 6
                  0 0 1       7 8 9
                  
  Input : 1 0 0,0 1 0,0 0 1|1 2 3,4 5 6,7 8 9
## 3 After submitting a matrix, run worker.java

## 4 finally check your client and server console.
  
