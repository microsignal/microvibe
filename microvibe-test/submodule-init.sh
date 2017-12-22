currdir=$PWD

if [ -n "$1" ];then
	submodule=$1
	mkdir -p "${submodule}"/src/main/java
	mkdir -p "${submodule}"/src/main/resources
	mkdir -p "${submodule}"/src/test/java
	mkdir -p "${submodule}"/src/test/resources
	
	touch "${submodule}"/src/main/java/README.md
	touch "${submodule}"/src/main/resources/README.md
	touch "${submodule}"/src/test/java/README.md
	touch "${submodule}"/src/test/resources/README.md
	
	cp pom-submodule.xml ${submodule}/pom.xml
	echo "Please edit ${submodule}/pom.xml"
fi

cd $currdir
