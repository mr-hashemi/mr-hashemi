---
layout: post
title:  "تعریف تابع"
date:   2019-12-15 00:55:22 +0100
categories: jekyll update
permalink: /bebin/
---
توابع در زبان برنامه‌نویسی آقای هاشمی با `bebin` تعریف می‌شوند.
در ادامه ساختار یک تابع را مشاهده می‌کنید.
پس از کلمه‌کلیدی `bebin` نام تابع، در اینجا `esme_functione_bebin` نوشته شده است. 
سپس ورودی‌های مورد نظر برای تابع را داخل پرانتز مشخص می‌کنیم. 
توجه داشته باشید که مقادیری که درون پرانتز تعریف شده است، برای اسکوپ داخلی تابع، قابل دسترسی هستند و اصطلاحا local هستند. 

{% highlight java %}
bebin esme_functione_bebin(voroodi) {
    khorooji = voroodi; 
    //    دستورهای مورد نظر برای این تابع تا 
    //    پیش از رسیدن به دستور «بده» نوشته می‌شود
    bede khorooji;
}
{% endhighlight %} 

تعریف تابع، مشابه چیزی که در بالا مشاهده می‌کنید،‌به خودی خود قرار نیست نتیجه‌ای در بر داشته باشد. بنابرین، می‌بایست تابع مورد نظر «صدا زده شود» یا «call» بشود. 
به این ترتیب ما خواهیم توانست بارها از قابلیت تابع در کدهای خودمان استفاده کنیم بدون آن‌که مجبور شویم تمام دستورات را بنویسم. فقط کافی است تابع صدا زده شود. 

## تابع `azinja`

در زبان برنامه‌نویسی آقای هاشمی، یک تابع خاص وجود دارد به نام `azinja` و این نام برای شروع برنامه‌ها رزرو شده است. 
به عبارت دیگر، `azinja` زمانی که یک فایل `.hashem` را در خط فرمان سیستم عامل خودتان اجرا می‌کنید، هسته‌ی زبان آقای هاشمی باید نقطه‌ی شروعی را انتخاب کند و به اجرای 
برنامه بپردازد. این سوال که هسته زبان آقای هاشمی **ازکجا** شروع می‌کند پاسخش `bebin azinja` است.

## مثال

یک نمونه تابع برای جمع دو عدد. این کد در فایلی با نام `jam_adad_ha.hashem` ذخیره می‌کنیم.

{% highlight java %}
bebin jam(alef, be) {
    javab = alef + be; 
    // هر بار جواب مورد نظر به بیرون از تابع فرستاده می‌شود
    bede javab;
}

bebin azinja() {
    ye_adad = 100;
    ye_adad_dige = 200;
    natije = jam(ye_adad, ye_adad_dige);
    bechap(natije);
}
{% endhighlight %} 

اجرای همین قطعه کد زبان هاشمی و نتیجه آن را در ادامه می‌بینید.

{% highlight bash %}
user@hashemi-lang-pc $ hashemi ./jam_adad_ha.hashem
300
user@hashemi-lang-pc $
{% endhighlight %}


## توابع بازگشتی

توابع بازگشتی یا recursive به توابعی گفته می‌شود که خودشان را صدا می‌زنند. 
در ریاضیات از این حالت برای تعریف تابع
 [فاکتوریل](https://fa.wikipedia.org/wiki/%D9%81%D8%A7%DA%A9%D8%AA%D9%88%D8%B1%DB%8C%D9%84)
  و یا برای 
  [نمایش سری فیبوناچی ](https://fa.wikipedia.org/wiki/%D8%A7%D8%B9%D8%AF%D8%A7%D8%AF_%D9%81%DB%8C%D8%A8%D9%88%D9%86%D8%A7%DA%86%DB%8C)
  استفاده می‌شود. 


{% highlight math %}
n! := 1             (if n := 0)
n! = n * (n-1)!     (otherwise)
{% endhighlight %}

پیاده سازی کد بازگشتی فاکتوریل در زبان برنامه نویسی آقای هاشمی :

{% highlight java %}
bebin factorial(n) {
   age (n < 1) bood {
      bede 1;
   }
   bede factorial(n-1) * n;
}

bebin azinja() {
    f = factorial(10);
    bechap(f);
}
{% endhighlight %} 

خروجی: 

{% highlight bash %}
user@hashemi-lang-pc $ hashemi ./factorial.hashem
3628800
user@hashemi-lang-pc $
{% endhighlight %}


## استفاده حرفه‌ای تر از توابع

برای محاسبه ترکیب ریاضی و محاسبه تعداد حالت های ممکن برای انخاب `k` عضو از یک مجموعه `n`  عضوی فرمول زیر را به کار می‌بریم 


![image](https://wikimedia.org/api/rest_v1/media/math/render/svg/3221a94c3c11b3dd8417d1db9005d0a7303c07dd)

اما در این فرمول سه بار از فاکتوریل استفاده شده است و ما در یک تابع جدید، تنها کافی است سه بار تابع `factorial` مثال قبلی را  فراخوانی کنیم

{% highlight java %}
bebin factorial(n) {
   age (n < 1) bood {
      bede 1;
   }
   bede factorial(n-1) * n;
}

bebin tarkib(k, n) {
    soorat = factorial(n);
    makhraj = factorial(k) * factorial(n-k);
    javab = soorat / makhraj;
    bede javab;
}

bebin azinja() {
    f = tarkib(5, 2);
    bechap(f);
}
{% endhighlight %} 
