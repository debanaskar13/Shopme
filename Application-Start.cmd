call docker build -f Dockerfile.backend.debug -t shopme-backend .
call docker run -it -p 8080:8080 shopme-backend
PAUSE