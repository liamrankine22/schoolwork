# minijvm makefile
# Computer Science 2211B
# Assignment 5
# Liam Rankine

#Creates required executables for proper program execution
all:    minijvm.o mjava

#Creates minijvm.o executable
minijvm.o:
        gcc -w -I include -c minijvm.c

#Creates mjava runnable file
mjava:
        gcc -w -L lib -o mjava minijvm.o -ladt

#Runs test1 after compiling
test1:  minijvm.o mjava
        ./mjava testfiles/test1

#Runs test2 after compiling
test2:  minijvm.o mjava
        ./mjava testfiles/test2

#Runs test3 after compiling
test3:  minijvm.o mjava
        ./mjava testfiles/test3

#Runs test4 after compiling
test4:  minijvm.o mjava
        ./mjava testfiles/test4

#Runs test5 after compiling
test5:  minijvm.o mjava
        ./mjava testfiles/test5

#Runs test6 after compiling
test6:  minijvm.o mjava
        ./mjava testfiles/test6

#Runs test7 after compiling
test7:  minijvm.o mjava
        ./mjava testfiles/test7

#Runs test8 after compiling
test8:  minijvm.o mjava
        ./mjava testfiles/test8

#Runs all tests ignoring errors (test 3)
test:   minijvm.o mjava
        -./mjava testfiles/test1
        -./mjava testfiles/test2
        -./mjava testfiles/test3
        -./mjava testfiles/test4
        -./mjava testfiles/test5
        -./mjava testfiles/test6
        -./mjava testfiles/test7
        -./mjava testfiles/test8

#Removes all executable files created (forced)
clean:
        -rm minijvm.o mjava
