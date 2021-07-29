# drools-or-with-collect-bug
When calling collect inside of an `or` expression the executable model will bind on the outer type and not on the collect type when doing the constraint checks.
