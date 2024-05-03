# Algorithms Final Project: Dithering
## Authors: Henry Skalla, Jay Lebakken, James McConnell

This program implements multiple dithering algorithms, including the context-based space-filling curve algorithm that is discussed in our write-up. This program was written for Prof. Arehalli's COMP221 course at Macalester College in Spring 2024. The program is written in Java.

### Using the program

The main method of the program is located in `App.java`. Upon running the file, the user is prompted in the command line to choose an image and a dithering algorithm. The user can list all available images using the command `list`.

### Known issues

 - Code is poorly documented, due to time constraints. Methods names should be self-explanatory, but we would have liked to add more documentation in the future.
 - The x and y coordinates are poorly conceptualized in reading images, so we encountered errors reading non-square images. Non-square images can now be read, but there could still be unexpected errors with the x and y coordinates due to poor tracking.
 - All images are rendered in their actual size, so some images are displayed too large for the screen or too small to see.
 - Window size is too small to view the entire path, so user must manually adjust window size when using the `cb-sfc-path` option.

 ### Outside credit
 - Baeldung: Help manipulating rgb values using bitwise operations. https://www.baeldung.com/java-rgb-color-representation#:~:text=In%20programming%20languages%2C%20including%20Java,into%20a%2032%2Dbit%20integer.
 - Baeldung: Getting rgb values from the BufferedImage class. https://www.baeldung.com/java-getting-pixel-array-from-image
 - Baeldung: Reading images filenames from directory. https://www.baeldung.com/java-list-directory-files
 - Test images are from the The USC-SIPI Image Database.
