# Do not change the following line . It specifies the base image which
# will be downloaded when you build your image .

FROM 

# Add all the files you need for your submission into the Docker image ,
# e.g. source code , Java bytecode , etc. In this example , we assume your
# program is the Python code in the file abc123 .py. For simplicity , we
# copy the file to the /bin directory in the Docker image . You can add
# multiple files if needed .

ADD DataFile.java /bin
ADD DataLine.java /bin
ADD Expression.java /bin
ADD ExpressionNode.java /bin
ADD ExpressionTools.java /bin
ADD ExpressionType.java /bin
ADD GeneticAlgorithm.java /bin
ADD MyCustomVector.java /bin
ADD Operators.java /bin
ADD PopulationGenerationMethod.java /bin
ADD Run.java /bin
ADD jsexp-0.2.2.jar /bin


# Install all the software required to run your code . The Docker image
# is derived from the Debian Linux distribution . You therefore need to
# use the apt -get package manager to install software . You can install
# e.g. java , python , ghc or whatever you need . You can also
# compile your code if needed .

# Note that Java and Python are already installed in the base image .

RUN javac -cp ./bin/jsexp-0.2.2.jar: -d ./bin/. ./bin/*.java

# The final line specifies your username and how to start your program .
# Replace abc123 with your real username and python /bin/ abc123 .py
# with what is required to start your program .

CMD ["-username", "", "-submission", "java -cp ./bin:./bin/jsexp-0.2.2.jar: Run"]
