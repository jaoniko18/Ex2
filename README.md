README
This readme is to explain code of assginment 2
Applied changes only to Scell and Ex2Sheet
In this assignment I have been working with different parts of programming in java, like:
Objected-Oriented programming (OOP) that includes all its aspects
Graphical User Interface (GUI) that Boaz already gave to me which includes table like excel,
Recursive funtctions, 
etc.
Main idea was to create an excel-like-table that works with string, numbers and forms and gui.
Also created new File Positions that is basically my coordinates (x, y)
Ex2Sheet was my main file with solution. Lets deep inside it
Compute: Recursive. Checks whether line starts with "=" gets rid of it. If it is a number than just parsing it and returning
helperCompute Recursive. Enters in to parenthesses and gives a soluion of evaluations that are inside (). Returns text, form, ERR_CYCLING, ERR_FORM directrly to the table
GetOpIndex: method helper that helps to find and indicate proper "depth" pf parenthesses 
parsePositon: checks if coordinates in the tables has a proper value and returns new coordinates 
containsCoord: basically checks whether the position exits
isIn: returns the dimension (length) of the x-coordinate of this spreadsheet
isOp: basically checks whether the operatos is '-' or '+' or '*' or '/'
signType: giving a proper evaluation due to an operator: '+' and '-' are least valuable, '*' nad '-' and parenthesses higher valuable
parseDouble: parses number into double if its possible if not throws an exception 

