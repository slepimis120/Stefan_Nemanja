import React, { useEffect, useState } from 'react';
import './Troop.css'; // Make sure to import the CSS file

const Troop = ({ troopTypes, onRemove, index, className, troopId, onChange }) => {
    const [troopType, setTroopType] = useState('');
    const [troopName, setTroopName] = useState('');
    const [troopCount, setTroopCount] = useState("0");
    const [health, setHealth] = useState("0");
    const [iPosition, setIPosition] = useState("0");
    const [jPosition, setJPosition] = useState("0");
    const [ammo, setAmmo] = useState("0");
    const [onMove, setOnMove] = useState(false);
    const [hasWaited, setHasWaited] = useState(false);

    useEffect(() => {
        onChange(index, {
            troopType,
            troopName,
            troopCount: parseInt(troopCount),
            health: parseInt(health),
            iPosition: parseInt(iPosition),
            jPosition: parseInt(jPosition),
            ammo: parseInt(ammo),
            onMove,
            hasWaited,
            id: troopId
        });
    }, [troopType, troopName, troopCount, health, iPosition, jPosition, ammo, onMove, hasWaited, troopId]);
    const handleRemove = () => {
        onRemove(index);
    };

    const handleTroopTypeChange = (e) => {
        const selectedTroop = troopTypes.find(troop => troop.name === e.target.value);
        setTroopType(selectedTroop.type);
        setTroopName(selectedTroop.name);
        if (selectedTroop && selectedTroop.type === 'RANGED') {
            setAmmo(selectedTroop.ammo);
        } else {
            setAmmo(0);
        }
        onChange(index, { troopType: selectedTroop.type, troopName, troopCount, health, iPosition, jPosition, ammo, onMove, hasWaited, id: selectedTroop.id });
    };

    const handleIPositionChange = (e) => {
        const value = parseInt(e.target.value);
        if (value >= 0 && value <= 10) {
            setIPosition(value);
        }
    };

    const handleJPositionChange = (e) => {
        const value = parseInt(e.target.value);
        if (value >= 0 && value <= 15) {
            setJPosition(value);
        }
    };

    const handleCountChange = (e) => {
        const value = parseInt(e.target.value);
        if (value >= 0) {
            setTroopCount(value);
        }
    };

    const handleHealthChange = (e) => {
        const value = parseInt(e.target.value);
        if (value >= 0) {
            setHealth(value);
        }
    };


    return (
        <div className={`troop-container ${className}`}>
            <div className="troop-fields">
                <label>
                    Troop Name:
                    <select className="troop-select" value={troopName} onChange={handleTroopTypeChange}>
                        <option value="" disabled>Select Troop Name</option>
                        {troopTypes.map((type, i) => (
                            <option key={i} value={type.name}>{type.name}</option>
                        ))}
                    </select>
                </label>
                <label>
                    Count:
                    <input type="number" value={troopCount} onChange={handleCountChange} placeholder="Count" />
                </label>
                <label>
                    Health:
                    <input type="number" value={health} onChange={handleHealthChange} placeholder="Health" />
                </label>
                <label>
                    i Pos:
                    <input type="number" value={iPosition} onChange={handleIPositionChange} placeholder="i Pos" />
                </label>
                <label>
                    j Pos:
                    <input type="number" value={jPosition} onChange={handleJPositionChange}  placeholder="j Pos" />
                </label>
                {troopType === 'RANGED' && (
                    <label>
                        Ammo:
                        <input type="number" value={ammo} onChange={(e) => setAmmo(parseInt(e.target.value))} placeholder="Ammo" />
                    </label>
                )}
                {className === "our-troop" && (
                    <label className="on-move-label">
                        <input type="checkbox" checked={hasWaited} onChange={(e) => setHasWaited(e.target.checked)} />
                        Has Waited:
                    </label>
                )}
                {className === "our-troop" && (
                    <label className="on-move-label">
                        <input type="checkbox" checked={onMove} onChange={(e) => setOnMove(e.target.checked)} />
                        On Move
                    </label>
                )}
            </div>
            <button className="remove-button" onClick={handleRemove}>Remove</button>
        </div>
    );
};

export default Troop;
