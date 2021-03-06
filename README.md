# Co-Make :house_with_garden: Build Week Project

### September 2020

## Back-End Developer:

| WEB Unit 4         |
| ------------------ |
| Christian Bautista |

## Co-Make API

### written in Java Spring

# Back-End

## API: https://bw-comakeapp-java.herokuapp.com/

### Table of Contents


[Important Endpoints](#important-endpoints)  
[Register and Login](#register-and-login)  
[User](#user)  
[Issue](#issue)  
[Comment](#comment)  
[Upvote](#upvote)  
[Role](#role)

## IMPORTANT ENDPOINTS


|  Type  |           Endpoint            |                   requires                   |            returns            |
| :----: | :---------------------------: | :------------------------------------------: | :---------------------------: |
|  GET   |      /oauth/revoke-token      |                    token                     |   logs user out of database   |
|  GET   |         /users/myinfo         |                    token                     |      current user's info      |
|  GET   |    /categories/categories     |                    token                     |     lists all categories      |
|  GET   |        /issues/issues         |                    token                     |       lists all issues        |
|  GET   |       /issues/issue/:id       |                    token                     |        get issue by id        |
|  GET   |         /users/users          |                    token                     |        lists all users        |
|  GET   |  /users/user/name/:username   |                    token                     |     get user by username      |
|  GET   |        /users/user/:id        |                    token                     |        get user by id         |
|  GET   |      /comments/comments       |                    token                     |       list all comments       |
|  GET   |     /comments/comment/:id     |                    token                     |      get comments by id       |
|  GET   |  /issues/issue/:id/comments   |                    token                     | list all comments in an issue |
|  POST  |        /createnewuser         |   username, phone, password, primaryemail    |             token             |
|  POST  |            /login             | username, password, client-id, client-secret |             token             |
|  POST  |         /issues/issue         |    full issue object without user, token     |        CREATED status         |
|  POST  |  /issues/issue/:id/comments   |            comment object, token             |        CREATED status         |
|  PUT   |       /issues/issue/:id       |    full issue object without user, token     |        ACCEPTED status        |
|  PUT   |        /users/user/:id        |           full user object, token            |        ACCEPTED status        |
|  PUT   | /issues/issue/:id/comment/:id |            comment object, token             |        ACCEPTED status        |
| DELETE |       /issues/issue/:id       |                    token                     |           OK status           |
| DELETE |        /users/user/:id        |                    token                     |           OK status           |
| DELETE |     /comments/comment/:id     |                    token                     |           OK status           |
| PATCH  |   /issues/issue/:id/upvote    |                update object                 |           OK status           |
| PATCH  |        /users/user/:id        |          partial user object, token          |        ACCEPTED status        |


## REGISTER AND LOGIN

#### The login axios request should look like

```
axios.post('https://bw-comakeapp-java.herokuapp.com/', `grant_type=password&username=${credentials.username}&password=${credentials.password}`, {
      headers: {
        // btoa is converting our client id/client secret into base64
        Authorization: `Basic ${btoa('XXXXXXX:XXXXXXXX')}`,
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
```

#### When registering a user the minimum required is

```
{
    "username": "someusername",
    "phone": "(231) 456-79845"
    "password": "somepassword",
    "primaryemail": "someEmail@email.local"
}
```

| Type |    Endpoint    |          What it does          |                          required                           |
| :--: | :------------: | :----------------------------: | :---------------------------------------------------------: |
| POST | /createnewuser | register user and return token | **username**, **phone**, **primaryemail**, and **password** |
| POST |     /login     | logs in user and returns token |                **username** and **password**                |
| GET  |    /logout     | removes user token from store  |                                                             |

## USER

In order to change any user information the request must come from an admin or  
the corresponding user to the id provided in the endpoint.

#### The user object is of a structure

```
{
    "userid": 3,
    "username": "admin",
    "phone": "(317)867-5309",
    "password": "somepassword",
    "primaryemail": "admin@email.local",
    "avatarimage": null,
    "roles": [],
    "issues": []
}
```

|  Type  |          Endpoint           |                                      What it does                                      |              required              |
| :----: | :-------------------------: | :------------------------------------------------------------------------------------: | :--------------------------------: |
|  GET   |        /users/users         |                               Returns full list of users                               |         Authenticated User         |
|  GET   |      /users/user/{id}       |                              Returns specific user by id                               |         Authenticated User         |
|  GET   |        /users/myinfo        |                             Returns current user's object                              |         Authenticated User         |
|  GET   | /users/user/name/{username} |                           Returns specific user by username                            |         Authenticated User         |
|  GET   | /user/name/like/{substring} |          Returns a list of users whose username contains the given substring           |         Authenticated User         |
|  POST  |       /createnewuser        | Creates new user to database with 'USER' as default role and returns status of CREATED |                None                |
|  POST  |         /users/user         |  Adds new user to database given a complete User Object and returns status of CREATED  |        Token and Admin role        |
|  PUT   |      /users/user/{id}       |               Replaces entire user by id and returns status of ACCEPTED                | Token, Admin role, and User object |
| DELETE |      /users/user/{id}       |                      Deletes user by id and returns status of OK                       |         Authenticated User         |

## ISSUE

In order to change any issue information the request must come from an admin or  
the corresponding user to the issue id provided in the endpoint.

#### The issue object is of a structure

```
{
    "issueid": 4,
    "title": "Some Title",
    "description": "This is the body of the issue where they decribe stuff.",
    "image": "image URL goes here",
    "category": {}
    "user": {}
    "comments": []
}
```

|  Type  |      Endpoint       |                        What it does                        |              required               |
| :----: | :-----------------: | :--------------------------------------------------------: | :---------------------------------: |
|  GET   |   /issues/issues    |                Returns full list of issues                 |         Authenticated User          |
|  GET   | /issues/issues/{id} |                Returns specific issue by id                |         Authenticated User          |
|  POST  |    /issues/issue    |  Adds new issue to database and returns status of CREATED  | Authenticated User and issue object |
|  PUT   | /issues/issue/{id}  | Replaces entire issue by id and returns status of ACCEPTED | Authenticated User and issue object |
| DELETE | /issues/issue/{id}  |        Deletes issue by id and returns status of OK        |         Authenticated User          |

## COMMENT

In order to change any comment information the request must come from an admin or  
the corresponding user to the issue id provided in the endpoint.

#### The comment object is of a structure

```
[
    {
        "commentid": 27,
        "comment": "This is a sample comment.",
        "user": {
            "userid": 23,
            "username": "taja",
            "phone": "(123)456-7777",
            "primaryemail": "taja@lambda.com",
            "avatarimage": null
        }
    }
]
```


|  Type  |            Endpoint             |                                   What it does                                   |               required                |
| :----: | :-----------------------------: | :------------------------------------------------------------------------------: | :-----------------------------------: |
|  GET   |       /comments/comments        |                          Returns full list of comments                           |          Authenticated User           |
|  GET   |     /comments/comment/{id}      |                         Returns specific comments by id                          |          Authenticated User           |
|  GET   |   /issues/issue/{id}/comments   |             Returns full list of comments in a specific issue by id              |          Authenticated User           |
|  POST  |   /issues/issue/{id}/comments   |        Adds new comment to a specific issue and returns status of CREATED        | Authenticated User and comment object |
|  PUT   | /issues/issue/{id}/comment/{id} | Replaces entire comment by id to a specific issue and returns status of ACCEPTED | Authenticated User and comment object |
| DELETE |     /comments/comment/{id}      |                  Deletes comment by id and returns status of OK                  |          Authenticated User           |

## UPVOTE

In order to increment the upvote count information the request must come from an authenticated user

#### The comment object is of a structure

```
{
    "upvote": 45
}
```

| Type  |         Endpoint          |                   What it does                   |      required      |
| :---: | :-----------------------: | :----------------------------------------------: | :----------------: |
|  GET  | /issues/issue/{id}/upvote | Returns the upvote value of specific issue by id | Authenticated User |
| PATCH | /issues/issue/{id}/upvote | Updates the upvote value of specific issue by id | Authenticated User |

## ROLE

#### The role object is of a structure

```
{
    "roleid": 1,
    "name": "admin",
    "users": []
}
```

|  Type  |     Endpoint     |                       What it does                        |       required        |
| :----: | :--------------: | :-------------------------------------------------------: | :-------------------: |
|  GET   |   /roles/roles   |                Returns full list of roles                 | Token and Admin role  |
|  GET   | /roles/role/{id} |                Returns specific role by id                | Token and Admin role  |
|  POST  |   /roles/role    |  Adds new role to database and returns status of CREATED  | Token and role object |
|  PUT   | /roles/role/{id} | Replaces entire role by id and returns status of ACCEPTED | Token and role object |
| DELETE | /roles/role/{id} |        Deletes role by id and returns status of OK        |         Token         |
