### University of Texas at Dallas—Department of Computer Science 
### CS 6380.001 Distributed Computing—Fall 2019
### Project 1 Description

This is a group project: 
Done by group of two members.
1) Anirudh Erabelly
2) Ravi Talari

You will develop a simple simulator that simulates a synchronous network using multi-threading. 
There are n+1 threads in the system: Each of the n processes will be simulated by one thread and there is one master thread. 
The master thread will “inform” all threads when a currentRound starts.

Thus, each thread simulating one process, before it can begin currentRound x, must wait for the master thread for a "go ahead" signal for currentRound x. 
Clearly, the master thread can give the signal to start currentRound x to the threads only if the master thread knows that all the n threads (simulating n processes) have completed their previous currentRound (currentRound x-1).
Your simulation will simulate the Variable Speeds algorithm in a synchronous ring. 
No process knows the value of n. The code (algorithm) executed by all (the n newly created threads) must be the same.

The input for this problem consists of (1) n (the number of processes of the distributed system which is equal to the number of threads to be created) and (2) one array id[n] of size n; 
the ith element of this array gives the unique id of the ith process or ith thread. 
The ith process accesses the ith element of array id[], and finds its unique id.
The master thread reads input file input.dat containing these two inputs and then spawns n threads. 
The file input.dat is a text file and all process ids are positive integers. Only the master thread knows n and the full array id[n].
Each process (each thread that simulates one process of the distributed system) should output its id and the id of the leader on the screen, and then terminate.
Upload one tar file containing your source code, a README file that tells us how to compile and run, the sample input file (input.dat) and the result of running your program (script file output) on your sample input file.

Due date: September 19, 2019, 11:55 pm.