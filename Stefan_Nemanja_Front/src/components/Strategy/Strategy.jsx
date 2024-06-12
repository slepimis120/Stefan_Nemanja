import React, { useEffect, useState } from 'react';
import Troop from './Troop';
import './Strategy.css';
import SpellSelection from "./SpellSelection"; // Make sure to import the CSS file

const Strategy = () => {
    const [ourTroops, setOurTroops] = useState([]);
    const [enemyTroops, setEnemyTroops] = useState([]);
    const [allTroops, setAllTroops] = useState([]);
    const [allSpells, setAllSpells] = useState([]);
    const [terrain, setTerrain] = useState('');
    const [spellPoints, setSpellPoints] = useState(0);
    const [usedSpell, setUsedSpell] = useState(false);
    const [selectedSpells, setSelectedSpells] = useState([]);
    const [showPopup, setShowPopup] = useState(false);
    const [popupContent, setPopupContent] = useState('');

    const token = localStorage.getItem('jwt');

    const handleTroopChange = (index, newTroopData, troops, setTroops) => {
        const newTroops = [...troops];
        newTroops[index] = { ...newTroopData, id: troops[index].id };
        setTroops(newTroops);
    };

    useEffect(() => {
        // Fetch all troops and spells from the server when the component loads
        fetch('http://localhost:8080/troop/allTroops', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => setAllTroops(data));

        fetch('http://localhost:8080/spell/allSpells', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => response.json())
            .then(data => setAllSpells(data));
    }, [token]);

    const handleAddTroop = (troops, setTroops) => {
        setTroops([...troops, {}]);
    };

    const handleRemoveTroop = (index, troops, setTroops) => {
        setTroops(troops.filter((_, i) => i !== index));
    };

    const handleLogout = () => {
        // Implement logout logic here
        // For example:
        localStorage.removeItem('jwt');
        // Redirect user to login page
        window.location.href = '/login';
    };

    const handleSubmit = async () => {
        const ourTroopsWithIds = ourTroops.map(troop => {
            const troopId = allTroops.find(t => t.name === troop.troopName)?.id;
            const { troopName, troopType, ...troopWithoutNameAndType } = troop;
            return { ...troopWithoutNameAndType, id: troopId };
        });

        const enemyTroopsWithIds = enemyTroops.map(troop => {
            const troopId = allTroops.find(t => t.name === troop.troopName)?.id;
            const { troopName, troopType, ...troopWithoutNameAndType } = troop;
            return { ...troopWithoutNameAndType, id: troopId };
        });

        const data = {
            ourTroops: ourTroopsWithIds,
            enemyTroops: enemyTroopsWithIds,
            availableSpells: selectedSpells,
            terrain: { type: terrain },
            spellPoints,
            usedSpell
        };

        try {
            const response = await fetch('http://localhost:8080/strategy/bestMove', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(data)
            });

            const responseData = await response.json();
            setPopupContent(responseData.result);
            setShowPopup(true);
        } catch (error) {
            console.error('Error:', error);
            // Handle error if necessary
        }

    };

    const handlePopupClose = () => {
        setShowPopup(false);
    };


    return (
        <div className="strategy-background">
            <div className="strategy-container">
                <div className="logout-container">
                    <button className="logout-button" onClick={handleLogout}>Logout</button>
                </div>
                <div className="header">
                    <h1>STEFAN NEMANJA 🦅🦅🦅</h1>
                    <hr/>
                </div>

                <div className="button-container">
                    {ourTroops.length < 7 && (
                        <button onClick={() => handleAddTroop(ourTroops, setOurTroops)}>Add Our Troop</button>
                    )}
                    {enemyTroops.length < 7 && (
                        <button onClick={() => handleAddTroop(enemyTroops, setEnemyTroops)}>Add Enemy Troop</button>
                    )}
                </div>

                <div className="troop-section">
                    <div>
                        <h2>Our Troops</h2>
                        <div className="troop-list">
                            {ourTroops.map((troop, index) => (
                                <Troop key={index} index={index} troopTypes={allTroops}
                                       onRemove={() => handleRemoveTroop(index, ourTroops, setOurTroops)}
                                       className="our-troop" troopId={troop.id}
                                       onChange={(index, newTroopData) => handleTroopChange(index, newTroopData, ourTroops, setOurTroops)}/> // Pass the handleTroopChange function as a prop
                            ))}
                        </div>
                    </div>

                    <div>
                        <h2>Enemy Troops</h2>
                        <div className="troop-list">
                            {enemyTroops.map((troop, index) => (
                                <Troop key={index} index={index} troopTypes={allTroops}
                                       onRemove={() => handleRemoveTroop(index, enemyTroops, setEnemyTroops)}
                                       className="enemy-troop" troopId={troop.id}
                                       onChange={(index, newTroopData) => handleTroopChange(index, newTroopData, enemyTroops, setEnemyTroops)}/>
                            ))}
                        </div>
                    </div>
                </div>

                <div className="input-section">
                    <label>
                        Select available spells:
                        <SpellSelection
                            spells={allSpells}
                            selectedSpells={selectedSpells}
                            setSelectedSpells={setSelectedSpells}
                        />
                    </label>
                    <label>
                        Select terrain:
                        <select value={terrain} onChange={(e) => setTerrain(e.target.value)}>
                            <option value="grass">GRASS</option>
                            <option value="dirt">DIRT</option>
                            <option value="lava">LAVA</option>
                            <option value="subterranean">SUBTERRANEAN</option>
                            <option value="rough">ROUGH</option>
                            <option value="sand">SAND</option>
                            <option value="snow">SNOW</option>
                            <option value="swamp">SWAMP</option>
                        </select>
                    </label>
                    <label>
                        Spell Points:
                        <input type="number" value={spellPoints} onChange={(e) => setSpellPoints(e.target.value)}/>
                    </label>
                    <label>
                        Used Spell:
                        <input type="checkbox" checked={usedSpell} onChange={(e) => setUsedSpell(e.target.checked)}/>
                    </label>
                    <button onClick={handleSubmit}>Submit</button>
                </div>
            </div>
            {showPopup && (
                <div className="popup-overlay">
                    <div className="popup-window">
                        <button onClick={handlePopupClose}>Close</button>
                        <p>{popupContent}</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default Strategy;
