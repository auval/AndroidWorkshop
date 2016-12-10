Android Development Workshop
==
**Shenkar** <br/>
[Amir Uval](shenkar@mindtheapps.com)


## Useful links
- [Android Developers YouTube Channel](https://www.youtube.com/user/androiddevelopers)
- [DebBites short videos](https://www.youtube.com/playlist?list=PLWz5rJ2EKKc_XOgcRukSoKKjewFJZrKV0)
- [Markdown](https://guides.github.com/pdfs/markdown-cheatsheet-online.pdf)
  The syntax used for this README.md file

## Workshop Lessons

---

### Lesson #1

[Slides](http://goo.gl/j8laap)

#### Installing & Configuring Android Studio

Install Android SDK's

**Recommended Plugins**:
  - .ignore
  - Markdown Support (JetBrain)
  - [Grep Console](https://github.com/krasa/GrepConsole)

**Useful Android Studio shortcuts**: [(And More Shortcuts)](https://gautam.io/work/android_studio_shortcuts/)
 
|	**Operation**	|	**Shortcut**	|
|	---	|	:---	|
|	Comment line	|	Ctrl+/	|
|	Duplicate line (or selection)	|	ctrl+d	|
|	Go to last edited location	|	ctrl+shift+Backspace	|
|	Jump to a particular type or file	|	Shift, Shift	|
|	Move lines up *or* down	|	ctrl+shift+up *or* ctrl+shift+down arrow	|
|	Navigate left	|	Alt+Left arrow (Mac: Command+Shift+[)	|
|	Navigate right	|	Alt+right arrow (Mac: Command+Shift+])	|
|	Navigate to the source definition	|	Windows+Click (Linux: ctrl+click, Mac: Command+click)	|
|	Open Settings	|	ctrl+alt+s	|
|	Organize the import statements	|	Ctrl+Alt+O	|
|	Reformat file	|	alt+ctrl+shit+L	|
|	Rename an object	|	Shift+F6	|
|	Search through files	|	Ctrl+Shift+F  (Mac: Command+Shift+F)	|

Tips:
* Use middle button of the mouse to select across rows, and edit all.

Homework: Create a project in Android studio and upload it to your GitHub account.

---

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
  - you can see the diff between 2 tags (or between branches) by adding `/compare` to the end of the github workspace URL. 
- git add .
  - adds all un-staged files to your local git index

---

### Lesson #3

[Slides](https://goo.gl/P2hWLP)

####class exercise


- Make main menu for exercises
  - Refactoring: rename MainActivity and related layout to Lesson2Activity and lesson2_layout
  - Create a new MainActivity to serve as a high level menu for exercises
  - Mark the new activity as MAIN in the manifest


---

### Lesson #4

[Slides](https://goo.gl/CXtCcy)

#### Code compare
- [Adding action bar](https://github.com/auval/AndroidWorkshop/compare/4...4.1)
- [Adding settings](https://github.com/auval/AndroidWorkshop/compare/4.1...4.2)
   - Simply create a new "Settings Activity" from the `new > activity` command menu.  
- [Enabling "< Back" from settings](https://github.com/auval/AndroidWorkshop/compare/4.2...4.2.1)
   - By overriding `onOptionsItemSelected()`
- [Showing how to read values from SharedPreferences](https://github.com/auval/AndroidWorkshop/compare/4.2.1...4.2.2)
  - And using AsyncHandler and UiHandler on the way
- [Showing how to write values to SharedPreferences from code](https://github.com/auval/AndroidWorkshop/compare/4.2.2...4.2.3)
- [Added SQL implementation and example](https://github.com/auval/AndroidWorkshop/compare/4.2.3...4.3)


#### print the content of the property file on the emulator
 `adb  -s emulator-5554 shell 'cat /data/data/org.shenkar.auval.codesamples/shared_prefs/org.shenkar.auval.codesamples_preferences.xml'`


#### [Playing with raw SQL](examples/SQLSample.md)

#### Tips

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

---

### Lesson 5

[Slides](https://goo.gl/gVRMt2)

- [Settings walkthrough](https://docs.google.com/document/d/1PVNrj7HtfjqAsHtnUnfLOSoTKthzemtfpbvQ-3CBE4U/edit?usp=sharing)
  Every student implements on his/her pc during class.

- Scene transition
    - example: [SceneTransitionActivity](https://github.com/auval/AndroidWorkshop/blob/master/app/src/main/java/org/shenkar/auval/codesamples/SceneTransitionActivity.java)
    - [diff](https://github.com/auval/AndroidWorkshop/compare/5...5.1)

---

### Lesson 6

    RecyclerView, Scene Transitions, Low Level Graphics

[Slides](https://goo.gl/hY7ALc)

- RecycleView (few words only + reference to code)
    - [Official Training]()
    - [Replacing ListView with RecycleView Diff](https://github.com/auval/AndroidWorkshop/compare/6...6.1)
- Low level graphics and animation

- [A lesson on Resources by Chaim Michael (with his permission)](https://youtu.be/SCLs7jyGs9k)

### Lesson 7

     Custom View Animation

[Slides](https://goo.gl/adGjJf)



---