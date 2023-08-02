package opa.authz
import future.keywords
import future.keywords.every

default allow := false

#Validate in order to GET, you must have read OR all permission
allow if {
    input.method == "GET"
    input.path = ["v1", "users"]
    allowed_roles := ["read", "all"]
    roles := {role | some role in input.user.authorities; role.authority = allowed_roles[_]}
    count(roles) > 0
}

#Validate in order to POST, you must have all permission
allow if {
    input.method == "POST"
    input.path = ["v1", "users"]
    roles := {role | some role in input.user.authorities; role.authority = "all"}
    count(roles) == 1
}

#Validate in order to PUT, you must have write permission + You are only updating your data
allow if {
    input.method == "PUT"
    input.path = ["v1", "users"]
    allowed_roles := ["write", "all"]
    roles := {role | some role in input.user.authorities; role.authority = allowed_roles[_]}
    upper(input.payload.firstname) = upper(input.user.username)
    count(roles) > 0
}