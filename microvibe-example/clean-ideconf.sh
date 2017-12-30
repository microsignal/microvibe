find . -name ".classpath" -exec rm {} \;
find . -name ".project" -exec rm {} \;
find . -type d -name ".settings" -exec rm -rf {} \;

find . -name "*.iml" -exec rm {} \;
find . -type d -name ".idea" -exec rm -rf {} \;
 
 