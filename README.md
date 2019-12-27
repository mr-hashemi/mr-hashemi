# Mr. Hashemi Language

> **Disclaimer**
>
> The contents of this repository built upon a clone of [graalvm/simplelanguage](https://github.com/graalvm/simplelanguage). repo
>
> commit: `15521b96322e5cdfe4d75c07334465e9d4a13a41`

Other languages are just too difficult. Ask Mr. Hashemi to do the job right for you!

![alt text](hashemi-logo.jpg) 


Mr. Hashemi is a *National* programming language.
We hope Mr. Hashemi get enough attention to make bilions of tomans out of this project.  

**example:**
```java
bebin azinja() {
  i = 0;
  ta (i<1) bood {
    bechap("adad bede");
    n = adadBekhoon();
    derakht(n);
  }
}

bebin derakht(n){
  i = 0;
  matn = "";

  ta (i <n) bood {
    matn = matn + "*";
    i = i + 1;
    bechap(matn);
  }

  j = 0;
  ta (j<4) bood {
    bechap("*");
    j = j+1;
  }
}
```  
  
## Basic syntax:  
### Functions:  
- Declare a function by keyword `bebin` before function name.  
- Program starts from main function.  
- to call a function simply call it.  
in example above the functions `main` and `derakht` are defined and function `derakht` is called inside main.

### Loops:  
The usual `while (condition) {code}` loop is defined as `ta (shart) bood {code}` .  
### condition:  
The usual `if (condition) {code}` conditions definition is defined as `age (shart) bood {code}` .  
### Variable declaration:  
- String :
	- matn = "ghoori ze ghalam, ghalam ze ghoori";
- Number :
	- adad = 313;
- Object :
	- chiz = jadid();
	- chiz.sefat1 = "hala ye chizi";
	- chiz.esm = "mammad";
	- chiz.sen = 33;
### logical operators:
Basically like java you can find the list [here](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/opsummary.html) .
