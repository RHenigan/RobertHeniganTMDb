# RobertHeniganTMDb
This is an application that will display Popular, Top Rated, Upcomong, and Now Playing Movies based on teh data retrieved from TMDb.com

IMPORTANT:
In order for this code to run you will need to create a values resource file to contain the api key.
To do so follow the sets below:
  1) After importing the project into android studio, right-click on the values directory inside of the res directory.
  2) Select New --> Values resource file
  3) In File Name type "api_key" and select "OK"
  4) Inside of the new file "api_key.xml" your file will need to look like this:
  
*Replace YOUR_API_KEY_HERE with your TMDb api key

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <item name="apiKey" type="string">YOUR_API_KEY_HERE</item>
</resources>
```

\nOnce the api_key resource file is created and has teh apiKey item defined the app can be built.

This applicaiton will launch into a Splash Activity which will welcome the user with a random quote from some of my favorite movies.
On the splash activity the user can select the "Popular Movies" button to advance to the Main Activity.

On the Main Activity the user will initially see a list of popular movies pulled from TMDb.
If there is no network connection established an alert dialog will ask the user to connect to the internet and refresh the page.
In the menu the refresh button will take the user back to the 1st page of results for the currently selected category.
In the menu overflow on the toolbar the user can select to display Popular, Top Rated, Upcoming, or Now Playing movies to be displayed.
If the user scrolls all the way to the bottom of list of movies the next page of resutls from TMDb will be loaded.
The menu also contains an information button to take the user to the information page.

On the Information page there is information about the app and recognition to:
  - TMDb.com for the movie database that was used to create this app.
  - material.io/icons for various icons used throughout the map.
  - IMDb.com for the quotes used in the splash activity.
