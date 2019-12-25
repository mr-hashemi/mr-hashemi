---
layout: post
title:  "تعریف متغیر"
date:   2019-12-25 14:53:22 +0100
categories: jekyll update
permalink: /variables/
---
برای تعریف متغیر تنها کافی است که نام متغیر را نوشته و سپس آن را مقداردهی کنیم.      


{% highlight java %}
nam = "Mr ";
famil = "Hashemi";  
tedadeBacheha = 3;  
mabda = "Kazeroon";  
maghsad = "Neishaboor"  
  
bechap("Aghaye "+nam+famil+", "+tedadeBacheha+" farzand darad.")  
// => ke chap khahad kard "Aghaye Mr Hashemi, 3 farzand darad."  
{% endhighlight %} 

برای افزودن کامنت در این زبان از دو شیوه استفاده می‌گردد.  یا استفاده از دو اسلش برای افزودن جزئیات (مانند نتیجه بچاپ در کد بالا) یا خط مورب و یک ستاره برای یک فضای کامنت شده.  توجه: قسمت های کامنت شده تنها برای مطالعه انسان و غیر قابل پردازش برای زبان آقای هاشمی هستند  

از خط مورب و دو ستاره برای تولید مستندات استفاده می‌شود.  

{% highlight java %}
// yek comment   
/*
in teke az code pardazesh nakhahad shod   
*/
/**  
 * baraye neveshtan e mostanadat  
 */

{% endhighlight %}