export MYSQL_DATABASE=moviedb
export MYSQL_USER_NAME=root
export MYSQL_PASSWORD=root
if [[ -z "${MYSQL_CI_URL}" ]]; then
export MYSQL_CI_URL=jdbc:mysql://localhost:3306/moviedb
fi
