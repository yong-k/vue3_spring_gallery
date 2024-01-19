function getNumberFormatted(val) {
    return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
}

export default getNumberFormatted