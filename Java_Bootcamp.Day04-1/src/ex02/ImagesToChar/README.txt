# This is an image converter tool
# This tool can convert images to characters and print to the console.

Usage:
app -w=<mask_white_symbol> -b=<mask_black_symbol>

Example:
app -w=RED -b=BLUE

Donwload lib:
mkdir lib
curl -o lib/jcommander-1.81.jar https://repo1.maven.org/maven2/com/beust/jcommander/1.81/jcommander-1.81.jar
curl -o lib/JCDP-4.0.0.jar https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar

To compile:
mkdir target
javac -cp lib/jcommander-1.81.jar:lib/JCDP-4.0.0.jar -d target src/java/edu.school21.printer/app/Program.java src/java/edu.school21.printer/logic/Converter.java src/java/edu.school21.printer/logic/ParseArgs.java
cp -R src/resources target/resources
cd target && jar xf ../lib/JCDP-4.0.0.jar && jar xf ../lib/jcommander-1.81.jar
rm -rf META-INF
cd ../

Create jar-archive
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target/ .

Run jar-file
java -jar target/images-to-chars-printer.jar -w=CYAN -b=YELLOW

Clear project
rm -rf lib/ target/