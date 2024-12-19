# Dramaturgy

**Table of Contents**

- [Dramaturgy](#dramaturgy)
- [What is Dramaturgy?](#what-is-dramaturgy-)
  * [Features](#features)
- [How to use it](#how-to-use-it)


# What is Dramaturgy?
Dramaturgy is a simple console application made in Kotlin that automatized your downloads, redirecting the files by extension or custom prefix depending of your personal configuration in a JSON file.
## Features
- Set what kind of files do you want in a certain folder by their extension.
- Set a prefix, so when you are downloading a file can make an exception to the extension set up.
- Add a list of words that you want to delete from the original name of the file you are downloading (blacklist).


# How to use it
**Make sure that the settingsDram.json is in the same directory as the Dramaturgy.jar**

1. Open the settingsDram.json.

2. Copy your Downloads directory path to the "downloadUrl".

3. Put all the extensions you want in the same directory inside "Extension" and put this directory inside "url"
4. In case you want so save your file in a specific folder inside the path setted in the json file, save your file with the name of the folder between parentheses.
> "funny (Shitpost).jpg" will be saved in "C:\\Users\\user\\Downloads\\Memes\\Shitpost"as"funny.jpg"

5. Words inside "blackList"will be deleted in the file name.
> "mySong OFFICIAL VIDEO.mp3" and "myImg OFFICIAL VIDEO.mp4" will be saved in "C:\\Users\\user\\Music"as "mySong.mp3" and  "myImg.mp4"

6. You can set custom prefix. ex: "-a" (any letter, uppercase or lowercase). A prefix allows you to make an exception of files even if their extension is settted above in the extensionsPath. 
> "-v mySong.mp3" will be saved in "C:\\Users\\user\\Desktop" instead of "C:\\Users\\user\\Music".

7. Save the settingsDram.json and run in a command prompt:
```
java -jar Dramaturgy-1.0-SNAPSHOT.jar
```
All the files saved in your "downloadUrl" will be moved based on the paths setted in the settingsDram.json.

### Example settingsDram.json:
```JSON
"downloadUrl" : "C:\\Users\\User\\Downloads",
	"extensionsPath":[
		{
			"extension" : [
				"mp3",
				"wav",
				"mp4"
			],
			"url": "C:\\Users\\user\\Music\\"
		},
		{
			"extension" : [
				"jpg",
				"png",
				"jpeg",
				"svg"
			],
			"url": "C:\\Users\\user\\Downloads\\Memes\\"
		}
	],
	"prefix" : [
		{
			"prefix" : "-v",
			"url": "C:\\Users\\user\\Desktop\\"
		},
		{
			"prefix": "-a",
			"url": "C:\\Users\\user\\Desktop\\Audio\\"
		}
	],
	"blackList" : [
		" OFFICIAL VIDEO"
	]
```
## Note
For Windows users must use the double backslash (\\), Linux and MacOs users must use normal slash (/)
### Windows
```
"url": "C:\\Users\\user\\Desktop\\"
```
### Linux
```
"url": "/home/user/Desktop/"
```

