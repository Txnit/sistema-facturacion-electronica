#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# Apache Maven Wrapper startup script, version 3.3.2
#
# Optional ENV vars
# -----------------
#   JAVA_HOME - location of a JDK home dir, required when download maven via java source
#   MVNW_REPOURL - repo url base for downloading maven distribution
#   MVNW_USERNAME/MVNW_PASSWORD - username and password for downloading maven
#   MVNW_VERBOSE - true: enable verbose log; debug: trace the maven wrapper script; others: silence the output
# ----------------------------------------------------------------------------

set -euf
[ "${MVNW_VERBOSE-}" != debug ] || set -x

# OS specific support.
native_path() { printf %s\\n "$1"; }
case "$(uname)" in
CYGWIN* | MINGW*)
  [ -z "${JAVA_HOME-}" ] || JAVA_HOME="$(cygpath --unix "$JAVA_HOME")"
  native_path() { cygpath --path --windows "$1"; }
  ;;
esac

# set JAVACMD which will be used by maven wrapper
JAVACMD="${JAVACMD-}"
if [ -z "${JAVACMD-}" ]; then
  if [ -n "${JAVA_HOME-}" ]; then
    if [ -x "$JAVA_HOME/jre/sh/java" ]; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD="$({ command -v java || which java || type java; } 2>/dev/null)" || :
  fi
fi
if [ ! -x "${JAVACMD-}" ]; then
  echo "Error: JAVA_HOME is not defined correctly." >&2
  echo "  We cannot execute ${JAVACMD}" >&2
  exit 1
fi

# traverses directory structure from process work directory to filesystem root
# first directory with .mvn subdirectory is considered project base directory
find_maven_basedir() {
  if [ -z "$1" ]; then
    echo "Path not specified to find_maven_basedir"
    return 1
  fi

  basedir="$1"
  wdir="$1"
  while [ "$wdir" != '/' ]; do
    if [ -d "$wdir"/.mvn ]; then
      basedir=$wdir
      break
    fi
    # workaround for JBEAP-8937 (on Solaris 10/Sparc)
    if [ -d "${wdir}" ]; then
      wdir=$(cd "$wdir/.." || exit 1; pwd)
    fi
    # end of workaround
  done
  printf '%s' "$(cd "$basedir" || exit 1; pwd)"
}

# concatenates all lines of a file
concat_lines() {
  if [ -f "$1" ]; then
    # Remove \r in case we run on Windows within Git Bash
    # and check out the repository with auto CRLF management
    # enabled. Otherwise, we may read lines that are delimited with
    # \r\n and produce $'-Xarg\r' rather than -Xarg due to word
    # splitting rules.
    tr -s '\r\n' ' ' < "$1"
  fi
}

log() {
  if [ "${MVNW_VERBOSE-}" = true ]; then
    printf '%s\n' "$1"
  fi
}

BASE_DIR=$(find_maven_basedir "$(dirname "$0")")
if [ -z "$BASE_DIR" ]; then
  exit 1;
fi

MAVEN_PROJECTBASEDIR=${MAVEN_BASEDIR:-"$BASE_DIR"}; export MAVEN_PROJECTBASEDIR
log "$MAVEN_PROJECTBASEDIR"

##########################################################################################
# Extension to allow automatically downloading the maven-wrapper.jar from Maven-central
# This allows using the maven wrapper in projects that prohibit checking in binary data.
##########################################################################################
wrapperJarPath="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
if [ -r "$wrapperJarPath" ]; then
    log "Found $wrapperJarPath"
else
    log "Couldn't find $wrapperJarPath, downloading it ..."

    if [ -n "${MVNW_REPOURL-}" ]; then
      wrapperUrl="$MVNW_REPOURL/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
    else
      wrapperUrl="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
    fi
    while IFS="=" read -r key value; do
      # Remove '\r' from value to allow usage on windows as IFS does not consider '\r' as a separator ( considers space, tab, new line ('\n'), and custom '=' )
      safeValue=$(printf '%s\n' "$value" | tr -d '\r')
      case "$key" in (wrapperUrl) wrapperUrl="$safeValue"; break ;;
      esac
    done < "$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"
    log "Downloading from: $wrapperUrl"

    if $MVNW_VERBOSE; then
      curl --fail --location --progress-bar "$wrapperUrl" --output "$wrapperJarPath"
    elif command -v wget > /dev/null; then
      log "Found wget ... using wget"
      if [ "$MVNW_VERBOSE" = true ]; then
        wget "$wrapperUrl" -O "$wrapperJarPath"
      else
        wget "$wrapperUrl" -O "$wrapperJarPath" 2> /dev/null || ret=$?
        if [ ${ret:-0} -ne 0 ]; then
          echo "Error: Failed to download '$wrapperUrl'." >&2
          exit 1
        fi
      fi
    elif command -v curl > /dev/null; then
      log "Found curl ... using curl"
      if [ "$MVNW_VERBOSE" = true ]; then
        curl --fail --location --progress-bar "$wrapperUrl" --output "$wrapperJarPath"
      else
        curl --fail --silent --location "$wrapperUrl" --output "$wrapperJarPath" 2> /dev/null || ret=$?
        if [ ${ret:-0} -ne 0 ]; then
          echo "Error: Failed to download '$wrapperUrl'." >&2
          exit 1
        fi
      fi
    else
      log "Falling back to using Java to download"
      javaSource="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/MavenWrapperDownloader.java"
      javaClass="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/MavenWrapperDownloader.class"
      # For Cygwin, switch paths to Windows format before running javac
      if $cygwin; then
        javaSource=$(cygpath --path --windows "$javaSource")
        javaClass=$(cygpath --path --windows "$javaClass")
      fi
      if [ -e "$javaSource" ]; then
        if [ ! -e "$javaClass" ]; then
          log " - Compiling MavenWrapperDownloader.java ..."
          ("$JAVACMD" "$javaSource")
        fi
        if [ -e "$javaClass" ]; then
          log " - Running MavenWrapperDownloader.java ..."
          ("$JAVACMD" -cp .mvn/wrapper MavenWrapperDownloader "$wrapperUrl" "$wrapperJarPath") || rm -f "$wrapperJarPath"
        fi
      fi
    fi
fi

# End of extension

# If specified, validate the SHA-256 sum of the Maven wrapper jar file
wrapperSha256Sum=""
while IFS="=" read -r key value; do
  case "$key" in (wrapperSha256Sum) wrapperSha256Sum=$value; break ;;
  esac
done < "$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"
if [ -n "$wrapperSha256Sum" ]; then
  wrapperSha256Result=false
  if command -v sha256sum > /dev/null; then
    if echo "$wrapperSha256Sum  $wrapperJarPath" | sha256sum -c > /dev/null 2>&1; then
      wrapperSha256Result=true
    fi
  elif command -v shasum > /dev/null; then
    if echo "$wrapperSha256Sum  $wrapperJarPath" | shasum -a 256 -c > /dev/null 2>&1; then
      wrapperSha256Result=true
    fi
  fi
  if [ $wrapperSha256Result = false ]; then
    echo "Error: Failed to validate Maven wrapper SHA-256, your Maven wrapper might be compromised." >&2
    echo "Investigate or delete $wrapperJarPath to attempt a clean download." >&2
    echo "If you updated your Maven version, you need to update the specified wrapperSha256Sum property." >&2
    exit 1
  fi
fi

MAVEN_OPTS="$(concat_lines "$MAVEN_PROJECTBASEDIR/.mvn/jvm.config") $MAVEN_OPTS"

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin; then
  [ -n "$MAVEN_PROJECTBASEDIR" ] &&
    MAVEN_PROJECTBASEDIR=$(cygpath --unix "$MAVEN_PROJECTBASEDIR")
  [ -n "$JAVA_HOME" ] &&
    JAVA_HOME=$(cygpath --unix "$JAVA_HOME")
  [ -n "$CLASSPATH" ] &&
    CLASSPATH=$(cygpath --path --unix "$CLASSPATH")
fi

# Provide a "standardized" way to retrieve the CLI args that will
# work with both Windows and non-Windows executions.
MAVEN_CMD_LINE_ARGS="$MAVEN_CONFIG $*"
export MAVEN_CMD_LINE_ARGS

exec "$JAVACMD" \
  $MAVEN_OPTS \
  $MAVEN_DEBUG_OPTS \
  -classpath "$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar" \
  "-Dmaven.multiModuleProjectDirectory=${MAVEN_PROJECTBASEDIR}" \
  ${MAVEN_OPTIONAL_OPTS} \
  org.apache.maven.wrapper.MavenWrapperMain \
  "$@"