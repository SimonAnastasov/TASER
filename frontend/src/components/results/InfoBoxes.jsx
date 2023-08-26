import React from 'react'

const InfoBoxes = ({ entities_list, global_texts }) => {
    return (
        <div className="lg:px-6 mx-auto mt-8 grid grid-cols-1 lg:grid-cols-3 gap-y-8 lg:gap-8">
            {/* Container on the left side of the global analysis dashboard */}
            <div className="relative bg-black/80 text-white shadow-md max-h-[400px] lg:max-h-[832px] overflow-y-scroll">
                <div className="sticky top-0 bg-black p-4 shadow-md">
                    <div className="w-fit">
                        <p className="heading--5">Entity Appearances</p>
                    </div>
                </div>
                
                <div className="py-2 px-4">
                    {/* A single entity_type holds many entities  */}
                    {/* Global_entities_list is 'key (entity_type) - value (list of entity objects)' object */}
                    {/* A single entity is 'key - value' object */}
                    {Object.entries(entities_list).map(([entity_type, entities]) => (
                        <div key={entity_type} className="mb-10">
                            <div className="w-fit">
                                <span className="heading--6">{entity_type}</span>
                                <hr/>
                            </div>
                            <div className="mt-4 pl-4">
                                {/* Sort entities so that the one with most appearances is on top */}
                                {[...entities].sort((a, b) => Object.values(b)[0] - Object.values(a)[0]).map((entity, index) => (
                                    <div key={`entity_${index}`}>
                                        <span className="heading--6 flex gap-2 justify-between items-end mb-3 border-y border-white/20">
                                            <span className="block leading-7">{Object.keys(entity)[0]}:</span>
                                            <span className="block leading-7">{Object.values(entity)[0]}</span>
                                        </span>
                                    </div>
                                ))}
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            {/* Container on the right side of the global analysis dashboard */}
            <div className="col-span-2 grid grid-cols-1 lg:grid-rows-2 gap-8">
                {global_texts.map((text, index) => (
                    <div key={`container_texts_${index}`}>
                        {/* Content container */}
                        <div key={`global_text_${index}`} className="relative bg-black/80 text-white shadow-md max-h-[400px] overflow-y-scroll">
                            {/* Sticky overlay on top holding the title */}
                            <div className="sticky top-0 bg-black p-4 shadow-md">
                                <div className="w-fit">
                                    <p className="heading--5">{text.title}</p>
                                </div>
                            </div>
                            {/* The content */}
                            <div className="heading--6 flex gap-2">
                                {/* Add numbers to indicate which line of text is being read */}
                                <div className="flex-shrink-0 bg-black py-2 px-2 shadow-md flex flex-col">
                                    {Array.from({ length: Math.floor(text.content.length / 20) }, (_, i) => i + 1).map((num) => (
                                        <span key={num} className="text-center">{num}</span>
                                    ))}
                                </div>
                                {/* Add the content */}
                                <div className="py-2 pl-2 pr-8 text-justify">{text.content}</div>
                            </div>
                        </div>
                        {/* End content container */}
                    </div>
                ))}
            </div>
        </div>
    )
}

export default InfoBoxes