Final 1 README

Engine requirements:
 - Non positional sound: there is a class AudioManager which is responsible for playing music and sound effects. This class has two methods: playMusic and playSound, which create a MusicThread or a SoundThread.
 - Text box sequences: there is a TextCutsceneScreen in ro7.engine.screens which read a plaintext file with the dialogue on the cutscene and shows it above the current screen.
 - Playing video: we changed this requirement to read a sequence of images and playing a slideshow with them, because using the video API was too hard for what we needed.
 - UI: the package ro7.engine.ui has the classes used for the HUD and the menu screens. The GameWorld now has a Hud object which contains a Set of HudElements and draw them above the viewport. To set the position of this elements, there is an enum called ScreenPosition, with the following positions: TOP_LEFT, TOP_CENTER, TOP_RIGHT, CENTER_LEFT, CENTER, CENTER_RIGHT, BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT.
 - Save game: since there is no playable game yet, the save game features were not implemented.

Known bugs: there are no known bugs

Approximate hours: 15 hours