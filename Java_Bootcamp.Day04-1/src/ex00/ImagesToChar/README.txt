# This is an image converter tool
# This tool can convert images to characters and print to the console.

Usage:
app <mask_white_symbol> <mask_black_symbol> <full_path_to_image>

Example:
app . 0 ./image.bmp

To compile:
mkdir target
javac -d target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/Converter.java

To run:
java -cp target edu.school21.printer.app.Program . 0 /mnt/d/PROJECTS/ClionProjects/S21/JavaBootcamp/Java_Bootcamp.Day04-1/src/ex00/ImagesToChar/src/resources/it.bmp