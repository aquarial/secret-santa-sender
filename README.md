### Secret Santa Sender


Parses a data file in the format `groupid, name, email`.

```
# from example-data.txt

0, A person, aperson@email.com
0, B person, bperson@email.com

1, C 3rd person, cperson@email.com

2, D last member, dperson@email.com
```


The program solves a possible route making sure nobody is giving a gift to 
someone in their group (if possible). For this example, A person gives to 
C 3rd person gives to B person gives to D last member gives to A person. 
Loop achieved. 

Uses your gmail login to send emails through smtp (SSL secured).
