FROM alpine:3.13.5
ARG BUILD_DATE
ARG VCS_REF

# renovate: datasource=repology depName=alpine_3_12/apache2-utils versioning=loose
ENV APACHE2_UTILS_VERSION="2.4.46-r3"

LABEL org.label-schema.build-date=$BUILD_DATE org.label-schema.vcs-ref=$VCS_REF \
    org.label-schema.vcs-url="https://github.com/yurake/k8s-3tier-webapp"

RUN apk --no-cache add apache2-utils=${APACHE2_UTILS_VERSION}
WORKDIR /home
COPY sample.json /home/.
