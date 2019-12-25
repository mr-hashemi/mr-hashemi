---
layout: post
title:  "نظارت بر جریان برنامه"
date:   2019-12-25 14:48:22 +0100
categories: jekyll update
permalink: /control-flow/
---
با استفاده از دستوراتی همچون اگر
age
و تا
ta
می‌توانیم بر جریان برنامه نظارت داشته باشیم.

`contron flow`
  
   
 به شکل نمونه در برنامه زیر در صورت درست بودن شرط متن چاپ شده خروجی
 `doroste`
 خواهد بود ولی در صورت نادرست بودن
 `dorost nist, ye fekri barash bokon`
 چاپ خواهد شد
   
{% highlight bash %}
shart = 1 == 1;  
    age (shart) bood {  
        bechap("doroste");  
        } na? {  
        bechap("dorost nist, ye fekri barash bokon");  
    }  
// => ke chap khahad kard "doroste"    
{% endhighlight %}   
  
با استفاده از کلیدواژه **تا** تا زمانی که شرط ما صحیح باشد خطوط نوشته شده در داخل حلقه اجرا خواهد شد. در حلقه زیر اعداد از یک تا ۱۰ با یکدیگر جمع شده و نتیجه در یک متغیر ذخیره می‌گردد.  
    
{% highlight bash %}
adadeMan = 0;  
majmoo = 0;  
  
ta (adadeMan<11) bood {  
    majmoo = majmoo + adadeMan;  
    adadeMan +=1;  
}  
  
bechap("majmoo barabar ast ba: "+majmoo);  
{% endhighlight %}   
