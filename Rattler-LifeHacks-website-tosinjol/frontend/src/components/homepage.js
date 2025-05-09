
import React, { useEffect, useState } from "react";

import axios from "axios";

import { Link } from "react-router-dom";

import Navbar from "./navbar"; // If you already have a Navbar, you can leave this in


export default function Homepage() {

    const [events, setEvents] = useState([]);


    useEffect(() => {

        axios

            .get("http://localhost:8080/api/events/") // Ensure this URL is correct

            .then((response) => setEvents(response.data.data))

            .catch((error) => console.error(error));

    }, []);


    return (

        <div className="home-page" style={styles.homePage}>

            <Navbar />

            <div className="container">

                {/* Banner Section */}

                <div className="banner">

                    <h1 className="banner-text">Welcome to the Hill, Rattlers</h1>

                </div>


                <div className="main">



                   

                </div>


                {/* NavBar has been removed */}

            </div>

        </div>

    );

}


const styles = {

    container: {

        flex: 1,

        paddingBottom: "80px", // Add bottom padding to avoid overlap if needed

        position: "relative", // Ensure it's positioned correctly
        zIndex: 1,
    },

    header: { backgroundColor: "#228B22", padding: "20px" },

    headerText: { color: "white", fontSize: "24px", textAlign: "center" },

    subText: { color: "white", textAlign: "center" },

    main: { padding: "20px" },

    banner: {

        backgroundColor: "rgba(34, 139, 34, 0.7)", // Semi-transparent background for better contrast on banner

        padding: "20px", // Padding for spacing around the text

        textAlign: "center", // Center the text

    },

    bannerText: {

        color: "white", // Text color

        fontSize: "36px", // Font size for the banner text

        fontWeight: "bold", // Make the text bold

    },

    sectionHeader: { fontSize: "20px", marginVertical: "10px" },

    homePage: {

        position:"absolute",

        backgroundImage: "url('/assets/home.jpeg')", // The background image URL

        backgroundSize: "cover", // Make sure the image covers the entire background

        backgroundPosition: "center", // Center the image

        backgroundRepeat: "no-repeat",

        minHeight: "100hv", // Ensure the background covers the full height of the page

        display: "flex",

        width: "100%",

        height: "100vh",

        flexDirection: "column", // Allow elements to stack vertically

        overflow: "hidden",


        padding: 0,



    }

};


