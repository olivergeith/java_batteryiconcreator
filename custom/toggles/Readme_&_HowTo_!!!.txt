########################################################################################################
What are we doing here ?
########################################################################################################
This is the folder where you can put your own sets of toggles.
Create subfolder(s) in here for example "MySuperDuperToggles" and place your toogle png's in that folder.

The directory structure should look like this:

./custom/toggles/
           -->   (AOKP) OlliGToggles
           -->   (AOKP) OlliGTogglesWithText
           -->   MySuperDuperToggles
           -->   ...
           
After that restart the "Battery Icon Creator". 
Hopefully it has found your new Folder "MySuperDuperToggles" and will present it in the toggles dropdownbox.

The icons are resized if you create a flashable-zip to the defined toggle-heigth (Rom Settings)! 
So if you want to stay flexible it might be a good idea to put xhdpi icons in here, even if you are on a hdpi ROM. (As said...the icons are resized to fit your Rom-Presets!)


########################################################################################################
HowTo - Toggles...your own, custom toggles
########################################################################################################
- analyse your ROMS SystemUI.apk (unzip it with 7zip and look in the res/drawable-(x)hdpi folder 
- find the toggle icons in there 
- copy toggles into a new folder under ./custom/toggles/<setname> 
-      eg ./custom/toggles/MySuperDuperToggles
-      next to my predifined Togglesets!
****Hint: it might be a good idea to create another folder in there with the untouched original toggle icons from your Rom's SystemUI.apk
-      eg ./custom/toggles/MyOriginalToggles
- for AOKP Roms there are normally 36 icons named like toggle_*.png
- edit the toggles with some Paint-Tool ore replace them with some completely new toggles(maybe you downloaded some from the internet)
- make sure you have the correct filenames of your toggles (filenames need to be exactly like in your SystemUI.apk)
- (Re-)Start The Battery Icon Creator and find your new toggleset in the Dropdownbox there...
- choose your Toggleset and create your flashable zip 



########################################################################################################
Attention !!!
########################################################################################################
The 2 predefined Toggles in here "(AOKP)..." are for AOKP-Roms only! Stock Roms have different filenames. 
Don't flash these to Stock Roms !!!!
########################################################################################################
 