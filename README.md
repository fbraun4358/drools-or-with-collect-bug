# drools-missing-getters-update-bug
When calling a `setX()` method in the RHS of a rule and calling `update()` on the object and there is no corrospinding `getX()` method, the executable model will fail becasuse the metadata class for the object will throw an exception with the property being unknown.
