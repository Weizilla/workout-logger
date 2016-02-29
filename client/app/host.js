function getHost() {
    return process.env.API_HOST || "";
}

export default getHost;