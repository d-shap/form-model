Form model library
==================
Form model library is a form definition API and a form binding API.

Form model represents the essential part of the GUI or any other source.
For example, HTML page can contain many elements, but only some of them make sence for an application.
Form model is a description of this essential elements.
Then the source HTML page is binded with this description.
The result of binding is the binded elements.
An application can use this binded elements and do not care about how this elements were obtained from the source HTML page.

Form model library mediates between the source and the application and encapsulates the complexity of the source.

Form model library provides a form binding API, but does not bind the source with the form description itself.
The binding extension is needed to bind specific source with the form description.

XML definition
==============
Namespace: ```http://d-shap.ru/schema/form-model/1.0```

form
----
Attributes:
* ```group``` - the form's group, optional
* ```id``` - the form's ID, mandatory

Attributes ```group``` and ```id``` identify the form and should be unique.

Child elements:
* ```element```
* ```single-element```
* ```form-reference```

element
-------
Attributes:
* ```id``` - the element's ID, optional
* ```lookup``` - the element's lookup string, used by the binding extension, mandatory
* ```type``` - the element's type, optional

Valid values for the ```type``` attribute depend on the parent element.
Possible values are:
* ```required``` - there should be exactly one element.
* ```required+``` - there should be at least one element.
* ```optional``` - there could be one element or no element at all.
* ```optional+``` - there could be more then one element or no element at all.
* ```prohibited``` - there should not be any one element.

Child elements:
* ```attribute```
* ```element```
* ```single-element```
* ```form-reference```


Donation
========
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
