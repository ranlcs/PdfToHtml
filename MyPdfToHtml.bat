@echo off
set a="D:/Projet/Java/SourceMesClasses/PdfToHtml"
javac *.java -cp %A%/bin/jar/itextpdf.jar -d bin
cd bin
java -cp %A%/bin;%A%/bin/jar/itextpdf.jar Frame
cd..
@echo on
