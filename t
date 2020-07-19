MAVEN(1)                                                                                    User Commands                                                                                    MAVEN(1)

NNAAMMEE
       mvn - Apache Maven software project management and comprehension tool

DDEESSCCRRIIPPTTIIOONN
       usage: mvn [options] [<goal(s)>] [<phase(s)>]

OOPPTTIIOONNSS
       --aamm,--also-make
              If project list is specified, also build projects required by the list

       --aammdd,--also-make-dependents
              If project list is specified, also build projects that depend on projects on the list

       --BB,--batch-mode
              Run in non-interactive (batch) mode (disables output color)

       --bb,--builder <arg>
              The id of the build strategy to use

       --CC,--strict-checksums
              Fail the build if checksums don't match

       --cc,--lax-checksums
              Warn if checksums don't match

       --ccppuu,--check-plugin-updates
              Ineffective, only kept for backward compatibility

       --DD,--define <arg>
              Define a system property

       --ee,--errors
              Produce execution error messages

       --eemmpp,--encrypt-master-password <arg>
              Encrypt master security password

       --eepp,--encrypt-password <arg>
              Encrypt server password

       --ff,--file <arg>
              Force the use of an alternate POM file (or directory with pom.xml)

       --ffaaee,--fail-at-end
              Only fail the build afterwards; allow all non-impacted builds to continue

       --ffff,--fail-fast
              Stop at first failure in reactorized builds

       --ffnn,--fail-never
              NEVER fail the build, regardless of project result

       --ggss,--global-settings <arg>
              Alternate path for the global settings file

       --ggtt,--global-toolchains <arg>
              Alternate path for the global toolchains file

       --hh,--help
              Display help information

       --ll,--log-file <arg>
              Log file where all build output will go (disables output color)

       --llllrr,--legacy-local-repository
              Use Maven 2 Legacy Local Repository behaviour, ie no use of _remote.repositories. Can also be activated by using --DDmmaavveenn.legacyLocalRepo=true

       --NN,--non-recursive
              Do not recurse into sub-projects

       --nnpprr,--no-plugin-registry
              Ineffective, only kept for backward compatibility

       --nnppuu,--no-plugin-updates
              Ineffective, only kept for backward compatibility

       --nnssuu,--no-snapshot-updates
              Suppress SNAPSHOT updates

       --oo,--offline
              Work offline

       --PP,--activate-profiles <arg>
              Comma-delimited list of profiles to activate

       --ppll,--projects <arg>
              Comma-delimited list of specified reactor projects to build instead of all projects. A project can be specified by [groupId]:artifactId or by its relative path

       --qq,--quiet
              Quiet output - only show errors

       --rrff,--resume-from <arg>
              Resume reactor from specified project

       --ss,--settings <arg>
              Alternate path for the user settings file

       --tt,--toolchains <arg>
              Alternate path for the user toolchains file

       --TT,--threads <arg>
              Thread count, for instance 2.0C where C is core multiplied

       --UU,--update-snapshots
              Forces a check for missing releases and updated snapshots on remote repositories

       --uupp,--update-plugins
              Ineffective, only kept for backward compatibility

       --vv,--version
              Display version information

       --VV,--show-version
              Display version information WITHOUT stopping build

       --XX,--debug
              Produce execution debug output

Apache Maven 3.5.0                                                                             May 2017                                                                                      MAVEN(1)
