XHTML Entities for Android
==========================

This utility class encodes and decodes HTML and XML entities optimized for Android.  
([Html](https://developer.android.com/reference/android/text/Html.html) does not support all entities like i.e. '„' (&amp;bdquo; or &amp;#8222;)).

The goal is highly performant conversion with a minimum of memory footprint. Best for frequently usage of relatively short strings like you will find in XML or HTML text elements or attribute values. So Regular Expressions were not an option.  
It does not support streaming, which would require more overhead. And also wrappers like [StringReader](http://docs.oracle.com/javase/6/docs/api/java/io/StringReader.html) for [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html) and [StringWriter](http://docs.oracle.com/javase/6/docs/api/java/io/StringWriter.html) for [StringBuilder](http://docs.oracle.com/javase/6/docs/api/java/lang/StringBuilder.html) have a lower performance compared to the wrapped classes.

The flow is optimized for the most probably occurence of characters in Roman languages, which means ASCII characters lower than 128 are most expected. 
The apdaption for the Android platform utilizes [SparseArray](http://developer.android.com/reference/android/util/SparseArray.html) instead of [Map<Integer, String>](http://docs.oracle.com/javase/6/docs/api/java/util/Map.html) and [ContentValues](http://developer.android.com/reference/android/content/ContentValues.html) instead of [Map<String, Integer>](http://docs.oracle.com/javase/6/docs/api/java/util/Map.html).  
_Feel free to change the code for non-Android environments!_


License
=======

    Copyright 2013 Dittmar Steiner

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

