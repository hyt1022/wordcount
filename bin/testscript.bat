wc.exe -c file1.c -o output1.txt
wc.exe -c file2.c -o output2.txt
wc.exe -w file3.c -o output3.txt
wc.exe -w file4.c -e stoplist.txt -o output4.txt
wc.exe -w file5.c -e stoplist2.txt -o output5.txt
wc.exe -l file6.c -o output6.txt
wc.exe -a file7.c -o output7.txt
wc.exe -a file8.c -o output8.txt
wc.exe -a file9.c -o output9.txt
wc.exe -a file10.c -o output10.txt
wc.exe -l file1.c
wc.exe -w -s *.c -o output12.txt
wc.exe -c -w -l -a -s *.c -e stoplist.txt -o output13.txt