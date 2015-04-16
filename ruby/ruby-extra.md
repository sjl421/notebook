# extra

```ruby
a = %{a b c}   #=> "a b c"
a = %q{a b c}  #=> 'a b c'
a = %Q{a b c}  #=> "a b c"
a = %w(a b c)  #=> ["a", "b", "c"]
a = %i(a b c)  #=> [:a, :b, :c]
a = %r(abc)    #=> /abc/
a = 1.2r       #=> (6/5)
0...5          #=> 0 1 2 3 4
0..5           #=> 0 1 2 3 4 5
```

* `%{}`和`%Q{}`创建双引号字符串,会在创建时发生字符串替换和转义

* `def` returns the symbolic name of the method instead of nil.
* `hash`es preserve the order of insertion.

## new method for ruby 2.1

### Array

* Array#rotate
* Array#keep_if
* Array#sample
* Array#repeated_permutation
* Array#repeated_combination

### Hash

* Hash#to_h
* Hash#default_proc=
* Hash#key
* Hash#keep_if
* Hash#assoc
* Hash#rassoc
* Hash#flatten
* Hash#compare_by_identity

### Enumerable

* Enumerable#to_h
* Enumerable#flat_map
* Enumerable#each_entry
* Enumerable#each_with_object
* Enumerable#chunk
* Enumerable#
* Enumerable#
* Enumerable#
* Enumerable#
