# RentAHome
Codepath project rentahome app.

Original App Design Project
===

# RentAHome

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This app is primarily used to rate existing apartments/landlords within cities. It's no secret in big cities that renters are usually taken advantage of. This app will help shed light on such an issue by providing a source/database to assess the conduct of landlords. 

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category: Business**
- **Mobile: Uses Camera. Mobile experience**
- **Story: Landlords are able to manage multiple properties and the collection of rent payment on this app. The App also allows Rentees to review homes from other renters as well as acting as a payment system between rentees and landlords**
- **Market: Real Estate \ Rentals. A lot of people may come to the app for easy access to rent ads as well as ways to manage their property without dealing with renting firms. Very handy data source for Real Estate / Rental Market research.**
- **Habit: The users would have to come back every month for payment dues. It is not very addictive as it is more of an app that you should/have to use whenever you have to pay your dues.**
- **Scope: The core feature of the app is reviews. More features can be implemented such as managing properties, managing payments, etc..**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* [Account Login]
* [Account creation]
* [Review]
* [Guest login]
* [Compose rent ad]
* [Posting rent ad]
* [Navigation menu]
* [sign out]
* [Display written review]
* [Rating]

**Optional Nice-to-have Stories**

* [Messaging]
* [Distance calculation]
* [Bookmarker]
* [payment system]
* [Edit profile]
* [Display rent history]
* [search up users]

### 2. Screen Archetypes

* [Login Screen]
   * Account login
   * Guest Login
* [Signup Screen]
   * Account creation
* [Compose Screen]
   * [Compose and post ad]
* [Feed screen]
   * [View rent ad]
* [Profile]
   * [Edit profile]
   * [Display rent history]
* [Review screen]
   * [Display written review]
   * [Rating]
* [Option menu]
   * [Sign out]
### 3. Navigation

**Tab Navigation** (Tab to Screen)

* [Guest View]
* [Sign up]
* [Login view]
* [Read review]
* [Profile screen]
* [Option menu]

**Flow Navigation** (Screen to Screen)

## Wireframes

<img src="https://github.com/RentaHomeCodepath/RentAHome/blob/main/IMG_0170.JPG" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models


User
Property | Type | Desciption 
--- | --- | --- 
username| string | username for login  
password| string | password for login
balance| double | How much money is in the User's balance
createdAt| DateTime |date when account is created


Review
Property | Type | Desciption 
--- | --- | --- 
author | pointer to User | review author
description | string | unique user experience describe by the author
likesCount| number| number of the like for the review
createdAt| DateTime |date when review is created
updatedAt| DateTime |date when review is updated
image| File| image of the house
objectId| string | unique id for the review
(optional) verify |boolean| If the review author is the past rentee


Post
Property | Type | Desciption 
--- | --- | ---
objectId| string | unique id for the post
review | custom review class | A set of data pertaining to the rating of the property
state | boolean | If the property is being rented out at the moment
Landlord | pointer to user | property's owner
(optional) rentee| array of pointer to user | past rentee that have rented the place
address| string | address of the property
price | number |cost per month



[Add table of models]
### Networking
* [Feed Screen]
   * (Read/Get) Query all the reviews
   * (create/Post) create reviews
   * (Delete) Delete existing like
   * (Delete) Delete existing review
   * (create/Post) create likes
* [Compose review Screen]
   * (create/Post) create reviews
   * (Delete) Delete existing review
* [Profile Screen]
   * (read/Get) Query Logged in user objects
   * (update/Put) Update user profile image
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
