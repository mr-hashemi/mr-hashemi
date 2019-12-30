# Mr. Hashemi Language

[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fmr-hashemi%2Fmr-hashemi.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fmr-hashemi%2Fmr-hashemi?ref=badge_shield)


Other languages are just too difficult. Ask Mr. Hashemi to do the job right for you!


![Hashemi's Familiy](hashemi-logo.jpg) 



Mr. Hashemi is a *National* programming language, a fork from [SimpleLanguage](https://github.com/graalvm/simplelanguage). 
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
- Program starts from `azinja` function.  
- to call a function simply call it.  
in example above the functions `azinja` and `derakht` are defined and function `derakht` is called inside `azinja`.

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


## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fmr-hashemi%2Fmr-hashemi.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fmr-hashemi%2Fmr-hashemi?ref=badge_large)
