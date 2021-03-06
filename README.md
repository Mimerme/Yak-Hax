# Yak-Hax
Yak Hax an unofficial vwrapper for the Yik Yak API in Java.

## Downloading and Installation
Yak Hax has no Maven repository. In order to download the library you can clone or download the repository go under releases/release.jar and include that in your build path. Other wise just view the raw file of release.jar in your browser to download it directly.

## What can YakHax do?
Here is a list of features from Yak Hax. Several features are not implemeneted and if propery reverse engineered can be implemented through getRequest and postRequest

| Feature                  | Request Type | Method Name                            |
|--------------------------|--------------|----------------------------------------|
| Get Yaks                 | GET          | getYaks                                |
| Get Area Top             | GET          | getAreaTop                             |
| Get My Recent Yak        | GET          | getMyRecentYaks                        |
| Get My Tops              | GET          | getMyTops                              |
| Get Recent Replies       | GET          | getMyRecentReplies                     |
| Post Yak                 | POST         | postYak                                |
| Delete Yak               | GET          | deleteYak                              |
| Upvote Yak               | GET          | upvoteYak                              |
| Downvote Yak             | GET          | downvoteYak                            |
| Get Yak Comments         | GET          | getYakComments                         |
| Post Comment             | POST         | postComment                            |
| Delete Comment           | GET          | downvoteComment                        |
| Upvote Comment           | GET          | upvoteComment                          |
| Downvote Comment         | GET          | downvoteComment                        |
| Report Comment           | GET          | reportComment                          |
| Start and Verify Account | POST         | multiple functions check documentation |
| Register New User        | POST         | check documentation                    |

## How to use the library
### Logging in
Must be called first before any requests are made
```java
YikYakAPI.login(userID, token, userAgent)
```

### Get Yaks
```java
YikYakAPI.getYaks(SortedMapParameters)
```

### Keeping up-to-date
Because Yik YaK's protocol does not change much across updates changing the Yik Yak version is often enough to make the cut
```java
YikYakAPI.setYikYakVersion(version)
```

### Register a new random user
Returns an array with 4 values in this specific order
* userid
* token
* deviceID
* user agent
```java
YikYakAPI.registerNewUser()
```

### Generic requests
If you have enough data on the GET and POST requests of the Yik Yak API not implemented already you can call these two methods
```java
YikYakAPI.getRequest(parameters)
YikYakAPI.postRequest(parameters)
```

### Values and parameters
There are a lot of parameters that Yik Yak uses when making their requests. Here are a few, they will be needed to make a Map of all the parameters. If it is a GET request the parameters are all in the query stirng. If it is a POST request some parameters are in the query string, while many are in the form data. Check the documentation to see which methods need what parameters.

- bc - needed, baescamp true/false, retrieved from YikYakProfile.java
- bypassedThreatPopup - needed 0 or 1
- userID - needed, get it from YikYakProfile.java
- deviceID - _used when registering a new user, not really needed_
- token - needed, get it from YikYakProfile.java
- version - needed get it from YikYakAPI.getYikYakVersion()
- lat - latitude, needed in parameters
- long - longitude, needed in parameters
- userLat - latitude, same as lat, needed
- userLong - longitude, same as long, needed
- accuracy - accuracy of the request, can be faked, needed
- altitude - altitude of the request, can be faked, needed
- comment - comment string, needed
- messageID - message id, needed, can be parsed from JSON
- reason - reason for reporting, needed
- commentID - comment id, needed, parsed from JSON
- message - message, needed

## License
Yak Hax uses the MIT License

## Documentation
All Yak Hax documentaiton is within the Github wiki

## Contributing
Clone the repository and import the project as a Maven project. Tools recomended include Bluestacks (to emualte the Android device), Charles (to capture requests from an Android device), and Fiddler (to debug the library and capture requests from Bluestacks). Contact me for information to give youself a head start.

## Credit
Yak Hax uses Maven. Many thanks to Soren21 for his repository as well as others from the Yik Yak hacking community. Do not use this library maliciously. All requests are monitered.
