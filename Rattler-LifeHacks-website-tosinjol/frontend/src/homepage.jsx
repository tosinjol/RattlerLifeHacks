import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';

export default function App() {
    return (
        <View style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.headerText}>Rattler Life Hacks</Text>
                <Text style={styles.subText}>Welcome to the Hill, Rattlers!</Text>
            </View>
            <View style={styles.main}>
                <Image
                    source={require('./welcome-banner.jpg')}
                    style={styles.banner}
                />
                <Text style={styles.sectionHeader}>Upcoming Events</Text>
                <View>
                    <Text>Event 1 - Date/Time</Text>
                    <Text>Event 2 - Date/Time</Text>
                    <Text>Event 3 - Date/Time</Text>
                </View>
            </View>
            <View style={styles.navBar}>
                <TouchableOpacity>
                    <Image source={require('./person-icon.png')} style={styles.icon} />
                    <Text>Info</Text>
                </TouchableOpacity>
                <TouchableOpacity>
                    <Image source={require('./home-icon.png')} style={styles.icon} />
                    <Text>Home</Text>
                </TouchableOpacity>
                <TouchableOpacity>
                    <Image source={require('./calendar-icon.png')} style={styles.icon} />
                    <Text>Calendar</Text>
                </TouchableOpacity>
                <TouchableOpacity>
                    <Image source={require('./rattler-icon.png')} style={styles.icon} />
                    <Text>Balance</Text>
                </TouchableOpacity>
                <TouchableOpacity>
                    <Image source={require('./clock-icon.png')} style={styles.icon} />
                    <Text>Wait Times</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1 },
    header: { backgroundColor: '#228B22', padding: 20 },
    headerText: { color: 'white', fontSize: 24, textAlign: 'center' },
    subText: { color: 'white', textAlign: 'center' },
    main: { padding: 20 },
    banner: { width: '100%', height: 200 },
    sectionHeader: { fontSize: 20, marginVertical: 10 },
    navBar: { flexDirection: 'row', justifyContent: 'space-around', padding: 10, backgroundColor: '#222' },
    icon: { width: 24, height: 24 }
});