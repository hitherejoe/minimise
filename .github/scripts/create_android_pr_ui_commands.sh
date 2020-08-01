git fetch --unshallow
git fetch origin

# Get all the modules that were changed
while read line; do
  module_name=${line%%/*}
  if [[ ${MODULES} != *"${module_name}"* ]]; then
    MODULES="${MODULES} ${module_name}"
  fi
done < <(git diff --name-only origin/$GITHUB_BASE_REF)
changed_modules=$MODULES

# Get a list of all available gradle tasks
AVAILABLE_TASKS=$(./gradlew tasks --all)

# Check if these modules have gradle tasks
build_commands=""
for module in $changed_modules
do
  if [[ $AVAILABLE_TASKS =~ ":platform_android"$module":connectedCheck" ]]; then
    build_commands=${build_commands}" "${module}":platform_android:connectedCheck
  fi
done

echo "./gradlew ${build_commands}"
