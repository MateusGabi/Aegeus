# Aegeus

Roadmap: https://trello.com/b/Djr9G7lN/aegeus-roadmap


## Build
`mvn clean compile assembly:single`

## Run
`java -jar aegeus.jar -f descritor.txt`

## Steps

The aegeus tool is split into three parts:
1. Download repo (main branch);
2. Download *n*th latest versions using git tags;
3. Assess cohesion metrics for each version; (This repo)
4. Read logs and store metric assessments on csv file;
5. Generate graphics using R Script and Python. (Code available on https://github.com/MateusGabi/Aegeus-scripts)

## Metrics

1. Service Interface Data Cohesion (SIDC);
1. Strict Service Implementation Cohesion (SSIC);
1. Lack Of Message Level Cohesion (LoCMes);
1. Number Of Operations (NO).
