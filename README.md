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
םשג
<img width="1710" alt="Screenshot 2025-01-12 at 15 28 58" src="https://github.com/user-attachments/assets/90cb4c2a-2bc5-4e9d-a4ba-b82408d2f78e" />
<img width="1710" alt="Screenshot 2025-01-12 at 15 30 18" src="https://github.com/user-attachments/assets/55889f0f-ae12-4550-86ef-6bff994ff4f3" />
<img width="1710" alt="Screenshot 2025-01-12 at 15 30 43" src="https://github.com/user-attachments/assets/13cfa8f6-e23d-428e-8a81-5dee5c49ef3f" />
<img width="1710" alt="Screenshot 2025-01-12 at 15 31 00" src="https://github.com/user-attachments/assets/f4bed1d2-7015-4042-b5c8-0d6a64fde83d" />
<img width="1710" alt="Screenshot 2025-01-12 at 15 31 08" src="https://github.com/user-attachments/assets/33d6fd81-fa2e-4d6c-bf36-0f1c03bcb7b0" />
