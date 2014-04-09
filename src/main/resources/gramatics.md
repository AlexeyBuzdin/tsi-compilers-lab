<pre>
&#60;ID>     ::= letter | &#60;ID> letter
&#60;INT>    ::= digit| &#60;INT> digit
&#60;DOT>    ::= .
&#60;COLON>  ::= :
&#60;EQUAL>  ::= =
&#60;DELIM>  ::= &#60;DOT> | &#60;COLON> | &#60;EQUAL> | @ | ( | ) | [ | ] | ; | , | &#60; | ^ | -
&#60;RANGE>  ::= &#60;DOT>&#60;DOT>
&#60;ASSIGN> ::= &#60;COLON>&#60;EQUAL>
</pre>