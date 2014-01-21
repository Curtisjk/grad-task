BT Grad Task - Phonetic Search
=========
This program implements the Phonetic Search algorithm as defined in the BT graduate scheme application task. It has been written in Java 7 (JDK 1.7.0_45) and tested on a Mac running OS X Mavericks in the terminal.

What's Included
---------------------
* Java source files
* Generated JavaDoc
* Samples `names.txt` file

How to Use
---------------------
This program has been tested on a Mac. Whilst there are no OS-specific function calls, it should be noted that I have not had chance to double-check this on a windows machine.

### Compiling

* Download and unzip the source files and navigate to the `src` folder using Terminal or Command Prompt.
* Run the `javac *.java` command to compile all of the Java files.

### Running the Program
In order to run the program, you must first of all have a file with one or more names defined within it - each on a new line. If you wish, you may use the `names.txt` file included as an example. This file must reside in the `src` folder.

You may run the program by running  `java PhoneticSearch {terms} < names.txt` where {terms} is one or more names you wish to search for (space delimited). For an example, see the next section.

### Expected Results and Sample Output
```
java PhoneticSearch Ericsson Smith < names.txt
Ericsson: Ericsson, Erikson
Smith: Smith, Smyth, Smythe, Smid, Schmidt
```

```
java PhoneticSearch Jones < names.txt
Jones: Jonas, Johns, Saunas
```

```
java PhoneticSearch Winton Mcdonald < names.txt
Winton: Van Damme
Mcdonald: Macdonald, Nest O'Malett
```

```
java PhoneticSearch Winston Mcdonald < names.txt
Winston: No results found.
```