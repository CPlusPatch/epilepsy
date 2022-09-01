package com.cpluspatch.epilepsy.api.machine;

import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

// not stolen from galacticraft GitHub (real)

public interface MachineStatus {
    MachineStatus NULL = new MachineStatus() {
        @Override
        public @NotNull Text getName() {
            return Text.literal("");
        }

        @Override
        public @NotNull StatusType getType() {
            return StatusType.OTHER;
        }
    };

    @NotNull Text getName();

    @NotNull StatusType getType();

    enum StatusType {
        /**
         * Machine is fully operational
         **/
        WORKING(true),

        /**
         * Machine is working at reduced efficiency
         **/
        PARTIALLY_WORKING(true),

        /**
         * The machine is missing a resource it needs to function.
         * Should not be an item, fluid or energy.
         *
         * @see #MISSING_ENERGY
         * @see #MISSING_FLUIDS
         * @see #MISSING_ITEMS
         */
        MISSING_RESOURCE(false),

        /**
         * The machine is missing a fluid it needs to function.
         * Should be preferred over {@link #MISSING_RESOURCE}
         */
        MISSING_FLUIDS(false),

        /**
         * The machine does not have the amount of energy needed to function.
         * Should be preferred over {@link #MISSING_RESOURCE}
         */
        MISSING_ENERGY(false),

        /**
         * The machine does not have the items needed to function.
         * Should be preferred over {@link #MISSING_RESOURCE}
         */
        MISSING_ITEMS(false),

        /**
         * The machine's output is blocked/full.
         */
        OUTPUT_FULL(false),

        /**
         * Everything else
         */
        OTHER(false);

        private final boolean active;

        StatusType(boolean active) {
            this.active = active;
        }

        public boolean isActive() {
            return this.active;
        }
    }
}