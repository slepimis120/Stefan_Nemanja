import React from 'react';
import './SpellSelection.css';

const SpellSelection = ({ spells, selectedSpells, setSelectedSpells }) => {
    const handleSpellClick = (spellId) => {
        const isSelected = selectedSpells.includes(spellId);

        if (isSelected) {
            setSelectedSpells(selectedSpells.filter(id => id !== spellId));
        } else {
            setSelectedSpells([...selectedSpells, spellId]);
        }
    };

    return (
        <div className="spell-selection">
            {spells.map(spell => (
                <div
                    key={spell.id}
                    className={`spell ${selectedSpells.includes(spell.id) ? 'selected' : ''}`}
                    onClick={() => handleSpellClick(spell.id)}
                >
                    {spell.name}
                </div>
            ))}
        </div>
    );
};

export default SpellSelection;
