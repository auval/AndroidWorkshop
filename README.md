Android Development Workshop
==
**Shenkar** <br/>
[Amir Uval](shenkar@mindtheapps.com)


## Quick references
- [Markdown](https://guides.github.com/pdfs/markdown-cheatsheet-online.pdf)
  The syntax used for this README.md file


## Workshop Lessons

### Lesson #1

[Slides](http://goo.gl/j8laap)

#### Installing & Configuring Android Studio


Settings: ctrl-alt-s

Install Android SDK's

**Recommended Plugins**:
  - .ignore
  - Markdown Support (JetBrain)
  - [Grep Console](https://github.com/krasa/GrepConsole)

Create a project in Android studio and upload it to your GitHub account.


### Lesson #2

[Slides](https://goo.gl/4EHTQi)

Useful **git** commands:

- git status
- git log
- git show: Show various types of objects
- git tag: tag specific points in history as being important
  - for example, these commits were tagged with annotation and comment:
      - git tag -a 2 -m "lecture #2"
      - git tag -a 2.1 -m "lecture #2.1"
  - you can now jump between them using:
      - git checkout 2  (..or 2.1)
- git add .
  - adds all unstaged files to your local git index

### Lesson #3

[Slides](https://goo.gl/P2hWLP)

####class exercise


- Make main menu for exercises
  - Refactoring: rename MainActivity and related layout to Lesson2Activity and lesson2_layout
  - Create a new MainActivity to serve as a high level menu for exercises
  - Mark the new activity as MAIN in the manifest


### Lesson #4

[Slides](https://goo.gl/CXtCcy)

- clone this workspace to your local pc
  - file -> new -> Project from version control -> GitHub
  - paste https://github.com/auval/AndroidWorkshop.git
  - define local directory for the workspace
  - clone

- creating App Toolbar activity
  - Follow the [tutorial](https://developer.android.com/training/appbar/index.html)
    - It's missing some important steps, so read here as well.
  - create an svg resource icon
    - New > “Vector Asset” from the context menu
  - define some menu options
    - New > “Android resource directory” from the context menu
    - Change the “Resource type” drop-down to be “menu”, then click OK to create the directory
    - right-click over your new res/menu/ directory and choose New > “Menu resource file” from the context menu
    - name it "lesson3.xml"
    - add \<item> resources, like the example in the tutorial or your own.
  - Add  `onCreateOptionsMenu(Menu menu) {}` to the activity, to inflate menu items

