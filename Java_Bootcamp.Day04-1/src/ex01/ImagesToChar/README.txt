# This is an image converter tool
# This tool can convert images to characters and print to the console.

Usage:
app <mask_white_symbol> <mask_black_symbol> <full_path_to_image>

Example:
app . 0 ./image.bmp

To compile:
mkdir target
javac -d target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/Converter.java

Copy resources
cp -R src/resources target/resources

Create jar-archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

Run jar-file
java -jar target/images-to-chars-printer.jar . 0 target/resources/it.bmp